package Server;

import Controller.Controller;
import Middleware.CompressionMiddleware;
import Middleware.Middleware;
import Middleware.MiddlewareManager;
import Request.Request;
import Request.RequestProcessor;
import Response.Response;
import Router.Router;

import java.io.InputStream;
import java.net.Socket;
import java.util.List;

public class HTTPConnectionHandler implements Runnable {

    private final Socket socket;
    private final MiddlewareManager middlewareManager;

    public HTTPConnectionHandler(Socket socket) {
        this.socket = socket;
        this.middlewareManager = initialiseMiddlewareManager();
    }

    @Override
    public void run() {
        System.out.println("Accepted a new connection");

        try {
            InputStream inputStream = this.socket.getInputStream();

            RequestProcessor requestProcessor = new RequestProcessor(inputStream);
            requestProcessor.processInputStream();
            requestProcessor.processRawRequest();
            Request request = requestProcessor.getRequest();
            middlewareManager.processRequest(request);

            Router router = new Router(request);
            Controller controller = router.routeRequest();
            Response response = controller.invoke();
            middlewareManager.processResponse(request, response);
            byte[] responseBytes = response.toByteArray();

            this.socket.getOutputStream().write(responseBytes);
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private MiddlewareManager initialiseMiddlewareManager() {
        List<Middleware> middleware = List.of(
                new CompressionMiddleware()
        );

        return new MiddlewareManager(middleware);
    }
}
