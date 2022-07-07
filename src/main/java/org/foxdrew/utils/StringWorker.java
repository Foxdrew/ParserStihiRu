package org.foxdrew.utils;

public class StringWorker {
    public static String clearHtmlTags(String text) {
        if (text != null) return text.replaceAll(Config.Patterns.htmlTags.toString(), "");
        return null;
    }
}
