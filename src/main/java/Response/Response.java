package Response;

import Enum.Header;
import Enum.StatusCode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Response {
    private final StatusCode statusCode;
    private final String statusMessage;
    private final Map<String, String> headers;
    private String bodyText;
    private byte[] bodyBinary;

    public Response(StatusCode statusCode, String statusMessage, Map<String, String> headers, String bodyText) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.headers = headers;
        this.bodyText = bodyText;
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

    public void setBody(String bodyText) {
        this.bodyText = bodyText;
        this.bodyBinary = null;
    }

    public void setBody(byte[] bodyBinary) {
        this.bodyBinary = bodyBinary;
        this.bodyText = null;
    }

    public String getBodyText() {
        return bodyText;
    }

    public byte[] getBodyBinary() {
        return bodyBinary;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        StringBuilder responseHeaders = new StringBuilder();
        responseHeaders.append("HTTP/1.1 ")
                .append(statusCode.getCode())
                .append(" ")
                .append(statusMessage)
                .append("\r\n");

        for (Map.Entry<String, String> header : headers.entrySet()) {
            responseHeaders.append(header.getKey())
                    .append(": ")
                    .append(header.getValue())
                    .append("\r\n");
        }
        responseHeaders.append("\r\n");

        byteArrayOutputStream.write(responseHeaders.toString().getBytes(StandardCharsets.UTF_8));

        if (bodyBinary != null) {
            byteArrayOutputStream.write(bodyBinary);
        } else if (bodyText != null) {
            byteArrayOutputStream.write(bodyText.getBytes(StandardCharsets.UTF_8));
        }

        return byteArrayOutputStream.toByteArray();
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
        if (bodyText != null) {
            response.append(bodyText);
        }

        return response.toString();
    }
}
