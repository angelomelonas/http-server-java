package Response;

import Enum.ContentType;
import Enum.Header;
import Enum.StatusCode;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private StatusCode statusCode;
    private String statusMessage;
    private final Map<String, String> headers = new HashMap<>();
    private String body;

    public ResponseBuilder() {
        // Default to HTTP 1.1 200 OK
        this.statusCode = StatusCode.OK;
        this.statusMessage = StatusCode.OK.getDescription();
    }

    public ResponseBuilder setStatusCode(StatusCode statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        return this;
    }

    public ResponseBuilder addHeader(Header header, String value) {
        headers.put(header.getHeaderName(), value);
        return this;
    }

    public ResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder setContentType(ContentType contentType) {
        headers.put(Header.CONTENT_TYPE.getHeaderName(), contentType.getContentType());
        return this;
    }

    public ResponseBuilder setContentLength(long contentLength) {
        if (body != null) {
            headers.put(Header.CONTENT_LENGTH.getHeaderName(), String.valueOf(contentLength));
        }
        return this;
    }

    public ResponseBuilder setContentLength() {
        if (body != null) {
            headers.put(Header.CONTENT_LENGTH.getHeaderName(), String.valueOf(body.length()));
        }
        return this;
    }

    public Response build() {
        return new Response(statusCode, statusMessage, headers, body);
    }
}
