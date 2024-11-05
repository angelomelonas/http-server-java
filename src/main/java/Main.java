import Connection.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static final int SERVER_PORT = 4221;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            do {
                Socket clientSocket = serverSocket.accept(); // Wait for connection from client.

                // Handle an incoming connection
                Runnable connectionHandler = new ConnectionHandler(clientSocket);
                new Thread(connectionHandler).start();
            } while (true);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}