package Controller;

import Enum.StatusCode;
import Request.Request;
import Response.Response;
import Response.ResponseBuilder;

public class RouteNotFoundController extends AbstractController {
    public RouteNotFoundController(Request request) {
        super(request);
    }

    public Response invoke() {
        return new ResponseBuilder()
                .setStatusCode(StatusCode.NOT_FOUND, StatusCode.NOT_FOUND.getDescription())
                .build();
    }
}
