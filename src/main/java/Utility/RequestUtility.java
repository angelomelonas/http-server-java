package Utility;

import Request.Request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtility {

    public static String getPath(Request request) throws Exception {
        Pattern pattern = Pattern.compile("^[A-Z]+ (/[^ ]*?)(?:/[^ ]*)?(?: HTTP)");
        Matcher matcher = pattern.matcher(request.getRequestLine());

        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new Exception("Url path not found.");
    }

    public static String getSlug(Request request) throws Exception {
        Pattern pattern = Pattern.compile("/([^ /]+) HTTP");

        Matcher matcher = pattern.matcher(request.getRequestLine());

        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new Exception("Slug not found.");
    }
}
