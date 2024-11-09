package Request;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private String requestLine;
    // Hashtable does not allow null keys and it's synchronized.
    private final Hashtable<String, String> headers;
    private final StringBuffer body;

    public Request(String rawRequest) {
        this.headers = new Hashtable<>();
        this.body = new StringBuffer();
        this.parseRequest(rawRequest);
    }

    public void parseRequest(String request) {
        try {
            BufferedReader reader = new BufferedReader(new StringReader(request));

            setRequestLine(reader.readLine());

            String header = reader.readLine();
            while (!header.isEmpty()) {
                appendHeaderParameter(header);
                header = reader.readLine();
            }

            String bodyLine = reader.readLine();
            while (bodyLine != null && !bodyLine.isEmpty()) {
                appendMessageBody(bodyLine);
                bodyLine = reader.readLine();
            }
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
        }
    }

    public void setRequestLine(String requestLine) throws Exception {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new Exception("Invalid Request.Request-Line");
        }

        this.requestLine = requestLine;
    }

    public void appendHeaderParameter(String header) throws Exception {
        if (header.isEmpty()) {
            return;
        }

        if (!header.contains(":")) {
            throw new Exception("Invalid header: " + header);
        }

        String[] headerValues = header.split(":");
        this.headers.put(headerValues[0].strip(), headerValues[1].strip());
    }

    private void appendMessageBody(String bodyLine) {
        this.body.append(bodyLine).append("\r\n");
    }

    public String getRequestLine() {
        return requestLine;
    }

    public String getHeaderParameter(String headerName) {
        return headers.get(headerName);
    }

    public String getBody() {
        return body.toString();
    }

    public String getRequestMethod() throws Exception {
        Pattern pattern = Pattern.compile("^[A-Z]+");
        Matcher matcher = pattern.matcher(requestLine);

        if (matcher.find()) {
            return matcher.group();
        }

        throw new Exception("HTTP method not found.");
    }
}
