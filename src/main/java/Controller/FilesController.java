package Controller;

import Enum.StatusCode;
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

            Path path = Paths.get("tmp/" + fileName);
            if (Files.exists(path)) {
                String fileContents = Files.readString(path);

                return new Response(StatusCode.OK, fileContents, "octet-stream");
            }

            return new Response(StatusCode.NOT_FOUND, StatusCode.NOT_FOUND.getDescription());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
