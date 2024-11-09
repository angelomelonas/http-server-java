package Middleware;

import Enum.Header;
import Request.Request;
import Response.Response;

public class CompressionMiddleware implements Middleware {
    @Override
    public void handleRequest(Request request) {
        // Nothing to do here.
    }

    @Override
    public void handleResponse(Request request, Response response) {
        String acceptEncoding = request.getHeaderParameter(Header.ACCEPT_ENCODING);

        if (acceptEncoding != null && acceptEncoding.equals("gzip")) {
            response.setHeader(Header.CONTENT_ENCODING, "gzip");
        }
    }
}
