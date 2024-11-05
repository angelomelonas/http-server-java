package Controller;

import Enum.StatusCode;
import Request.Request;
import Response.Response;
import Utility.RequestUtility;

public class UserAgentController extends AbstractController {
    public UserAgentController(Request request) {
        super(request);
    }

    protected Response getResponse() {
        try {
            String requestLine = RequestUtility.getSlug(this.request);

            return new Response(StatusCode.OK, "User-Agent: " + requestLine);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
