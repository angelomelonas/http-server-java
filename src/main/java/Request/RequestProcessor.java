package Request;

import java.io.IOException;
import java.io.InputStream;

public class RequestProcessor {

    private final InputStream inputStream;
    private String rawRequest = null;
    private Request request = null;

    public RequestProcessor(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void processInputStream() {
        try {
            this.rawRequest = this.getRawRequest();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public void processRawRequest() {
        this.request = new Request(this.rawRequest.stripTrailing());
    }

    private String getRawRequest() throws IOException {
        StringBuilder result = new StringBuilder();
        do {
            // Construct the request byte by byte, and converting each to a character.
            result.append((char) this.inputStream.read());
        } while (this.inputStream.available() > 0);

        return result.toString();
    }

    public Request getRequest() {
        return request;
    }
}