import HTTPServer.HTTPServer;

public class Main {

    public static void main(String[] args) {
        String directory = "";
        if (args[0] != null) {
            directory = args[0];
        }

        HTTPServer httpServer = new HTTPServer(4221, directory);
        httpServer.start();
    }
}