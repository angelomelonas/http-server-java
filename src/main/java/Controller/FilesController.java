package Controller;

import Enum.StatusCode;
import HTTPServer.HTTPServer;
import Request.Request;
import Response.Response;
import Utility.RequestUtility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FilesController extends AbstractController {
    public FilesController(Request request) {
        super(request);
    }

    protected Response getResponse() {
        try {
            String fileName = RequestUtility.getSlug(this.request);
            Path path = Paths.get(HTTPServer.DIRECTORY + fileName);

            if (Files.exists(path)) {
                int fileSize = (int) Files.size(path);
                String fileContents = Files.readString(path);

                return new Response(
                        StatusCode.OK,
                        fileContents,
                        "application/octet-stream",
                        fileSize
                );
            }

            return new Response(StatusCode.NOT_FOUND, "File Not Found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Response postResponse() {
        try {
            String fileName = RequestUtility.getSlug(this.request);
            Path path = Paths.get(HTTPServer.DIRECTORY + fileName);

            if (Files.exists(path)) {
                throw new IOException("File already exists");
            } else {
                Files.write(path, request.getBody().strip().getBytes());
            }

            return new Response(
                    StatusCode.CREATED,
                    StatusCode.CREATED.getDescription()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
