import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static final int SERVER_PORT = 4221;

    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        // Uncomment this block to pass the first stage

        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            Socket clientSocket = serverSocket.accept(); // Wait for connection from client.
            System.out.println("accepted new connection");
            InputStream inputStream = clientSocket.getInputStream();

            byte[] buffer = new byte[1024];
            inputStream.read(buffer);

            String request = new String(buffer);
            String[] requestLines = request.split("\r\n");

            String requestLine = requestLines[0];

            String[] requestLinesSplit = requestLine.split("\\s+");
            String method = requestLinesSplit[0];
            String target = requestLinesSplit[1];

            switch (method) {
                case "GET":
                    handleGetRequest(clientSocket, target);
                    break;
                default:
                    clientSocket.getOutputStream().write("HTTP/1.1 505 Method Not Allowed\r\n\r\n".getBytes());
            }

            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private static void handleGetRequest(Socket clientSocket, String target) throws IOException {
        if (target.equals("/")) {

            String response = createResponse(
                    "HTTP/1.1 200 OK",
                    new String[]{""},
                    ""
            );
            clientSocket.getOutputStream().write(response.getBytes());

            return;
        }

        if (target.startsWith("/echo/")) {
            String responseBody = target.split("/")[2];

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