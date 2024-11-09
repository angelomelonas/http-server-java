package Controller;

import Enum.Method;
import Enum.StatusCode;
import Request.Request;
import Response.Response;
import Response.ResponseBuilder;

public abstract class AbstractController implements Controller {
    protected final Request request;

    protected AbstractController(Request request) {
        this.request = request;
    }

    public Response invoke() throws Exception {
        Method method = Method.fromString(this.request.getRequestMethod());

        return switch (method) {
            case GET -> this.getResponse();
            case POST -> this.postResponse();
            case PUT -> this.putResponse();
            case PATCH -> this.patchResponse();
            case DELETE -> this.deleteResponse();
            default -> new ResponseBuilder()
                    .setStatusCode(StatusCode.NOT_FOUND, StatusCode.NOT_FOUND.getDescription())
                    .build();
        };
    }

    protected Response getResponse() throws Exception {
        return getNotAllowedResponse();
    }

    protected Response postResponse() throws Exception {
        return getNotAllowedResponse();
    }

    protected Response putResponse() {
        return getNotAllowedResponse();
    }

    protected Response patchResponse() {
        return getNotAllowedResponse();
    }

    protected Response deleteResponse() {
        return getNotAllowedResponse();
    }

    private Response getNotAllowedResponse() {
        return new ResponseBuilder()
                .setStatusCode(StatusCode.METHOD_NOT_ALLOWED, StatusCode.METHOD_NOT_ALLOWED.getDescription())
                .build();
    }
}
