package Controller;

import Enum.ContentType;
import Enum.StatusCode;
import Request.Request;
import Response.Response;
import Response.ResponseBuilder;
import Server.HTTPServer;
import Utility.RequestUtility;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesController extends AbstractController {
    public FilesController(Request request) {
        super(request);
    }

    protected Response getResponse() throws Exception {
        String fileName = RequestUtility.getSlug(this.request);
        Path path = Paths.get(HTTPServer.DIRECTORY + fileName);

        if (Files.exists(path)) {
            long fileSize = Files.size(path);
            String fileContents = Files.readString(path);

            return new ResponseBuilder()
                    .setContentType(ContentType.APPLICATION_OCTET_STREAM)
                    .setBody(fileContents)
                    .setContentLength(fileSize)
                    .build();
        }

        return new ResponseBuilder()
                .setStatusCode(StatusCode.NOT_FOUND, "File Not Found")
                .build();
    }

    protected Response postResponse() throws Exception {
        String fileName = RequestUtility.getSlug(this.request);
        Path path = Paths.get(HTTPServer.DIRECTORY + fileName);

        if (!Files.exists(path)) {
            Files.writeString(path, request.getBody());

            return new ResponseBuilder()
                    .setStatusCode(StatusCode.CREATED, StatusCode.CREATED.getDescription())
                    .build();
        }

        return new ResponseBuilder()
                .setStatusCode(StatusCode.CONFLICT, "File Already Exists")
                .setContentLength()
                .build();
    }
}
