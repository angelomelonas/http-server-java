package Controller;

import Request.Request;
import Response.Response;
import Response.ResponseBuilder;

public class IndexController extends AbstractController {
    public IndexController(Request request) {
        super(request);
    }

    protected Response getResponse() {
        return new ResponseBuilder()
                .build();
    }
}
