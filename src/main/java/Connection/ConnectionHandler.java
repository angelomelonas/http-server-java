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
            Request request = new Request(rawRequest.stripTrailing());

            Router router = new Router(request);
            Response response = router.routeRequest();

            this.socket.getOutputStream().write(response.toString().getBytes());
            this.socket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private String getRawRequest(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        do {
            // Construct the request byte by byte, and converting each to a character.
            result.append((char) inputStream.read());
        } while (inputStream.available() > 0);

        return result.toString();
    }
}
