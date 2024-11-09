package Middleware;

import Request.Request;
import Response.Response;

public interface Middleware {
    void handleRequest(Request request);

    void handleResponse(Request request, Response response);
}
