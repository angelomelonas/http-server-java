package Enum;

public enum Method {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    CONNECT("CONNECT");

    private final String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static Method fromString(String method) {
        for (Method m : Method.values()) {
            if (m.getMethod().equalsIgnoreCase(method)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + method);
    }

    @Override
    public String toString() {
        return method;
    }
}