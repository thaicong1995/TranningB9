package org.example.Assignment;

public class StringUtils {
    public static String sanitizeString(String input) {
        if (input == null) return "";
        String result = input.trim();

        result = result.replaceAll("[\\\\/:*?\"<>|]", "_");
        result = result.replaceAll("\\s+", "_");

        return result;
    }

//    public static String spilitMessage(String input) {
//        if (input == null) return "";
//        StringBuffer result = new StringBuffer();
//
//        result = result
//    }
}
