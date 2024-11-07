package Controller;

import Enum.StatusCode;
import Request.Request;
import Response.Response;

public class UserAgentController extends AbstractController {
    public UserAgentController(Request request) {
        super(request);
    }

    protected Response getResponse() {
        try {
            String userAgent = this.request.getHeaderParameter("User-Agent");

            return new Response(StatusCode.OK, userAgent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
