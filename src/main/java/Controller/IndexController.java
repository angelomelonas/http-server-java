package Controller;

import Enum.StatusCode;
import Request.Request;
import Response.Response;

public class IndexController extends AbstractController {
    public IndexController(Request request) {
        super(request);
    }

    protected Response getResponse() {
        return new Response(StatusCode.OK, "");
    }
}
