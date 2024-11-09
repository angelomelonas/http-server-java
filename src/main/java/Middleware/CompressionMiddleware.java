package Middleware;

import Enum.Header;
import Request.Request;
import Response.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HexFormat;
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
            HexFormat hex = HexFormat.of();
            response.setBody(hex.formatHex(compressedBody));
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

    private byte[] compressBodyGzip(Response response) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOs = new GZIPOutputStream(os)) {
            byte[] buffer = response.getBody().getBytes();
            gzipOs.write(buffer, 0, buffer.length);
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
