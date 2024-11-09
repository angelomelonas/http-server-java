package Controller;

import Enum.ContentType;
import Request.Request;
import Response.Response;
import Response.ResponseBuilder;
import Utility.RequestUtility;

public class EchoController extends AbstractController {
    public EchoController(Request request) {
        super(request);
    }

    protected Response getResponse() throws Exception {
        String requestSlug = RequestUtility.getSlug(this.request);

        return new ResponseBuilder()
                .setContentType(ContentType.TEXT_PLAIN)
                .setBody(requestSlug)
                .setContentLength()
                .build();
    }
}
