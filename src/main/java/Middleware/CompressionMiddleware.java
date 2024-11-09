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
        if (request.getHeaderParameter(Header.ACCEPT_ENCODING).equals("gzip")) {
            response.setHeader(Header.CONTENT_ENCODING, "gzip");
        }
    }
}
