package Controller;

import Enum.StatusCode;
import HTTPServer.HTTPServer;
import Request.Request;
import Response.Response;
import Utility.RequestUtility;

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
            System.out.println("File path: " + path);
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
}
