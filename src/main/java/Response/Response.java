package Response;

import Enum.Header;
import Enum.StatusCode;

import java.util.Map;

public class Response {
    private final StatusCode statusCode;
    private final String statusMessage;
    private final Map<String, String> headers;
    private String body;

    public Response(StatusCode statusCode, String statusMessage, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.headers = headers;
        this.body = body;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setHeader(Header header, String value) {
        this.headers.put(header.getHeaderName(), value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 ").append(statusCode.getCode()).append(" ").append(statusMessage).append("\r\n");

        // Append headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }

        response.append("\r\n");

        // Append body if present
        if (body != null) {
            response.append(body);
        }

        return response.toString();
    }
}
