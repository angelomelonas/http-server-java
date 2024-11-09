package Enum;

public enum Header {
    // General Headers
    DATE("Date"),
    CONNECTION("Connection"),
    KEEP_ALIVE("Keep-Alive"),
    CACHE_CONTROL("Cache-Control"),
    PRAGMA("Pragma"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    TRANSFER_ENCODING("Transfer-Encoding"),

    // Request Headers
    ACCEPT("Accept"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    USER_AGENT("User-Agent"),
    HOST("Host"),
    REFERER("Referer"),
    AUTHORIZATION("Authorization"),
    COOKIE("Cookie"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),

    // Response Headers
    LOCATION("Location"),
    SERVER("Server"),
    CONTENT_ENCODING("Content-Encoding"),
    EXPIRES("Expires"),
    LAST_MODIFIED("Last-Modified"),
    SET_COOKIE("Set-Cookie"),
    WWW_AUTHENTICATE("WWW-Authenticate"),
    X_POWERED_BY("X-Powered-By");

    private final String headerName;

    Header(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }

    @Override
    public String toString() {
        return headerName;
    }
}