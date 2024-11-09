package Server;

import Controller.Controller;
import Request.Request;
import Request.RequestProcessor;
import Response.Response;
import Router.Router;

import java.io.InputStream;
import java.net.Socket;

public class HTTPConnectionHandler implements Runnable {

    private final Socket socket;

    public HTTPConnectionHandler(Socket socket) {
        this.socket = socket;
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

            Router router = new Router(request);
            Controller controller = router.routeRequest();
            Response response = controller.invoke();

            this.socket.getOutputStream().write(response.toString().getBytes());
            this.socket.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
