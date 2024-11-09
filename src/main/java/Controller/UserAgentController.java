package Controller;

import Enum.ContentType;
import Enum.Header;
import Request.Request;
import Response.Response;
import Response.ResponseBuilder;

public class UserAgentController extends AbstractController {
    public UserAgentController(Request request) {
        super(request);
    }

    protected Response getResponse() {
        String userAgentHeader = this.request.getHeaderParameter(Header.USER_AGENT);

        return new ResponseBuilder()
                .setContentType(ContentType.TEXT_PLAIN)
                .setBody(userAgentHeader)
                .setContentLength()
                .build();
    }
}
