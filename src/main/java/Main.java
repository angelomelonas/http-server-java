import Server.HTTPServer;

public class Main {

    public static void main(String[] args) {
        String directory = null;

        for (int i = 0; i < args.length; i++) {
            if ("--directory".equals(args[i]) && i + 1 < args.length) {
                directory = args[i + 1];
                break;
            }
        }

        HTTPServer httpServer = new HTTPServer(4221, directory);
        httpServer.start();
    }
}