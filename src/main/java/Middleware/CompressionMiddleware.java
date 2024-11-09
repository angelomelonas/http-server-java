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
            compressBodyGzip(response);
            response.setHeader(Header.CONTENT_ENCODING, "gzip");
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

    private void compressBodyGzip(Response response) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOs = new GZIPOutputStream(os)) {
            byte[] buffer = response.getBody().getBytes();
            gzipOs.write(buffer, 0, buffer.length);
            response.setBody(os.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
