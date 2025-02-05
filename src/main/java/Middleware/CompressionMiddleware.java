package Middleware;

import Enum.Header;
import Request.Request;
import Response.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;

public class CompressionMiddleware implements Middleware {
    @Override
    public void handleRequest(Request request) {
        // Nothing to do here.
    }

    @Override
    public void handleResponse(Request request, Response response) {
        if (this.shouldCompressWithGzip(request)) {
            byte[] compressedBody = compressBodyGzip(response);

            response.setHeader(Header.CONTENT_ENCODING, "gzip");
            response.setHeader(Header.CONTENT_LENGTH, String.valueOf(compressedBody.length));
            response.setBody(compressedBody);
        }
    }

    private boolean shouldCompressWithGzip(Request request) {
        String acceptEncoding = request.getHeaderParameter(Header.ACCEPT_ENCODING);
        if (acceptEncoding != null) {
            String[] acceptEncodings = acceptEncoding.split(", ");
            return Arrays.stream(acceptEncodings).anyMatch("gzip"::equalsIgnoreCase);
        }

        return false;
    }

    private byte[] compressBodyGzip(Response response) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOs = new GZIPOutputStream(os)) {
            byte[] buffer = response.getBodyText().getBytes();
            gzipOs.write(buffer, 0, buffer.length);
            gzipOs.close();

            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
