package Controller;

import Enum.StatusCode;
import Request.Request;
import Response.Response;

public abstract class AbstractController {
    protected final Request request;

    protected AbstractController(Request request) {
        this.request = request;
    }

    public Response invoke() throws Exception {
        return switch (this.request.getRequestMethod()) {
            case "GET" -> this.getResponse();
            case "POST" -> this.postResponse();
            case "PUT" -> this.putResponse();
            case "PATCH" -> this.patchResponse();
            case "DELETE" -> this.deleteResponse();
            default -> new Response(StatusCode.NOT_FOUND, StatusCode.NOT_FOUND.getDescription());
        };
    }

    protected Response getResponse() {
        return new Response(StatusCode.METHOD_NOT_ALLOWED, StatusCode.METHOD_NOT_ALLOWED.getDescription());
    }

    protected Response postResponse() {
        return new Response(StatusCode.METHOD_NOT_ALLOWED, StatusCode.METHOD_NOT_ALLOWED.getDescription());
    }

    protected Response putResponse() {
        return new Response(StatusCode.METHOD_NOT_ALLOWED, StatusCode.METHOD_NOT_ALLOWED.getDescription());
    }

    protected Response patchResponse() {
        return new Response(StatusCode.METHOD_NOT_ALLOWED, StatusCode.METHOD_NOT_ALLOWED.getDescription());
    }

    protected Response deleteResponse() {
        return new Response(StatusCode.METHOD_NOT_ALLOWED, StatusCode.METHOD_NOT_ALLOWED.getDescription());
    }
}
