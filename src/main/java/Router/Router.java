package Router;

import Controller.EchoController;
import Controller.IndexController;
import Controller.UserAgentController;
import Enum.StatusCode;
import Request.Request;
import Response.Response;
import Utility.RequestUtility;

public class Router {
    private final Request request;

    public Router(Request request) {
        this.request = request;
    }

    public Response routeRequest() {
        try {
            return switch (RequestUtility.getPath(this.request)) {
                case "/" -> (new IndexController(this.request)).invoke();
                case "/echo" -> (new EchoController(this.request)).invoke();
                case "/user-agent" -> (new UserAgentController(this.request)).invoke();
                default -> new Response(StatusCode.NOT_FOUND, StatusCode.NOT_FOUND.getDescription());

            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
