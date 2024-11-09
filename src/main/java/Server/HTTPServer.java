package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
    public static int SERVER_PORT = 4221;
    public static String DIRECTORY = "";

    public HTTPServer(int port, String directory) {
        SERVER_PORT = port;
        DIRECTORY = directory;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            do {
                Socket clientSocket = serverSocket.accept(); // Wait for connection from client.

                // Handle an incoming connection
                Runnable connectionHandler = new HTTPConnectionHandler(clientSocket);
                new Thread(connectionHandler).start();
            } while (true);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
