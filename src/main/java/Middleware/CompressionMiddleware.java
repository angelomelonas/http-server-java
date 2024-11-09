package Middleware;

import Enum.Header;
import Request.Request;
import Response.Response;

import java.util.Arrays;

public class CompressionMiddleware implements Middleware {
    @Override
    public void handleRequest(Request request) {
        // Nothing to do here.
    }

    @Override
    public void handleResponse(Request request, Response response) {
        String acceptEncoding = request.getHeaderParameter(Header.ACCEPT_ENCODING);
        if (acceptEncoding != null) {
            String[] acceptEncodings = acceptEncoding.split(", ");
            if (Arrays.stream(acceptEncodings).anyMatch("gzip"::equalsIgnoreCase)) {
                response.setHeader(Header.CONTENT_ENCODING, "gzip");
            }
        }
    }
}
