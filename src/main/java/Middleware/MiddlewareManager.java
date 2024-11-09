package Middleware;

import Request.Request;
import Response.Response;

import java.util.List;

public class MiddlewareManager {
    private final List<Middleware> middlewares;

    public MiddlewareManager(List<Middleware> middlewares) {
        this.middlewares = middlewares;
    }

    public void processRequest(Request request) {
        for (Middleware middleware : middlewares) {
            middleware.handleRequest(request);
        }
    }

    public void processResponse(Request request, Response response) {
        for (Middleware middleware : middlewares) {
            middleware.handleResponse(request, response);
        }
    }
}
