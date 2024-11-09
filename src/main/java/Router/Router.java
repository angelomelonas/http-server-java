package Router;

import Controller.*;
import Request.Request;
import Utility.RequestUtility;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Request request;
    private final Map<String, Controller> routes;

    public Router(Request request) {
        this.request = request;
        this.routes = new HashMap<>();
        initializeRoutes();
    }

    private void initializeRoutes() {
        routes.put("/", new IndexController(this.request));
        routes.put("/echo", new EchoController(this.request));
        routes.put("/user-agent", new UserAgentController(this.request));
        routes.put("/files", new FilesController(this.request));
    }

    public Controller routeRequest() throws Exception {
        String path = RequestUtility.getPath(this.request);

        return routes.getOrDefault(path, new RouteNotFoundController(this.request));
    }
}
