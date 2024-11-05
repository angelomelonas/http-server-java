package Controller;

import Enum.StatusCode;
import Request.Request;
import Response.Response;
import Utility.RequestUtility;

public class EchoController extends AbstractController {

    public EchoController(Request request) {
        super(request);
    }

    protected Response getResponse() {
        try {
            String requestLine = RequestUtility.getSlug(this.request);

            return new Response(StatusCode.OK, requestLine);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
