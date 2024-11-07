package Response;

import Enum.StatusCode;

import java.util.Hashtable;

public class Response {
    private String statusLine;
    private StatusCode statusCode;
    private String body;
    private final Hashtable<String, String> headers;

    public Response() {
        this.headers = new Hashtable<>();
    }

    public Response(StatusCode statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = new Hashtable<>();
        this.createResponse("text/plain", body.trim().length());
    }

    public Response(StatusCode statusCode, String body, String contentType, int contentLength) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = new Hashtable<>();
        this.createResponse(contentType, contentLength);
    }

    private void createResponse(String contentType, int contentLength) {
        this.statusLine = "HTTP/1.1 " + statusCode.getCode() + " " + statusCode.getDescription();
        addHeader("Content-Type", contentType);
        addHeader("Content-Length", Integer.toString(contentLength));
    }

    public int getStatusCode() {
        return statusCode.getCode();
    }

    public String getBody() {
        return body;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Hashtable<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        StringBuilder responseString = new StringBuilder();
        responseString.append(statusLine).append("\r\n");
        this.headers.forEach((key, value) -> responseString.append(key).append(": ").append(value).append("\r\n"));
        responseString.append("\r\n");
        responseString.append(body);

        return responseString.toString();
    }
}
