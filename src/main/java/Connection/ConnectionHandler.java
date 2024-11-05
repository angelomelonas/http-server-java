package Connection;

import Request.Request;
import Response.Response;
import Router.Router;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Accepted a new connection");
        try {
            InputStream inputStream = this.socket.getInputStream();

            String rawRequest = getRawRequest(inputStream);
            Request request = new Request(rawRequest);

            Router router = new Router(request);
            Response response = router.routeRequest();

            this.socket.getOutputStream().write(response.toString().getBytes());
            this.socket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private String getRawRequest(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        inputStream.read(buffer);

        return new String(buffer);
    }
}
