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

            byte[] buffer = new byte[1024];
            inputStream.read(buffer);

            String rawRequest = new String(buffer);
            String[] rawRequestLines = rawRequest.split("\r\n");

            String request = rawRequestLines[0];
            String host = rawRequestLines[1];
            String header = rawRequestLines[2];

            String[] requestLines = request.split("\\s+");
            String[] headerLines = header.split(":");
            String method = requestLines[0];

            switch (method) {
                case "GET":
                    handleGetRequest(this.socket, headerLines, requestLines);
                    break;
                default:
                    this.socket.getOutputStream().write("HTTP/1.1 505 Method Not Allowed\r\n\r\n".getBytes());
            }

            this.socket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private static void handleGetRequest(
            Socket clientSocket,
            String[] headerLines,
            String[] requestLines
    ) throws IOException {
        String target = requestLines[1];

        if (target.equals("/")) {
            String response = createResponse(
                    "HTTP/1.1 200 OK",
                    new String[]{""},
                    ""
            );
            clientSocket.getOutputStream().write(response.getBytes());

            return;
        }

        if (target.startsWith("/echo")) {
            String responseBody = target.split("/")[2];

            String response = createResponse(
                    "HTTP/1.1 200 OK",
                    new String[]{"Content-Type: text/plain", "Content-Length: " + responseBody.length()},
                    responseBody
            );
            clientSocket.getOutputStream().write(response.getBytes());

            return;
        }

        if (target.startsWith("/user-agent")) {
            String responseBody = headerLines[1].trim();

            String response = createResponse(
                    "HTTP/1.1 200 OK",
                    new String[]{"Content-Type: text/plain", "Content-Length: " + responseBody.length()},
                    responseBody
            );

            clientSocket.getOutputStream().write(response.getBytes());

            return;
        }

        String response = createResponse(
                "HTTP/1.1 404 Not Found",
                new String[]{""},
                ""
        );
        clientSocket.getOutputStream().write(response.getBytes());
    }

    private static String createResponse(String status, String[] headers, String body) {
        return status + "\r\n" + String.join("\r\n", headers) + "\r\n\r\n" + body;
    }
}
