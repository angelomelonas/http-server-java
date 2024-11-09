package Enum;

public enum ContentType {
    TEXT_HTML("text/html"),
    APPLICATION_JSON("application/json"),
    TEXT_PLAIN("text/plain"),
    APPLICATION_XML("application/xml"),
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public static ContentType fromString(String contentType) {
        for (ContentType type : ContentType.values()) {
            if (type.getContentType().equalsIgnoreCase(contentType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unexpected Content-Type value: " + contentType);
    }
}