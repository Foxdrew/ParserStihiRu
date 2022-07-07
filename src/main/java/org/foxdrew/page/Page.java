package org.foxdrew.page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Page {
    protected final String url;
    protected final String responseBody;

    public Page(String url, String responseBody) {
        this.url = url;
        this.responseBody = responseBody;
    }

    public String getFirstMatch(Pattern pattern) {
        Matcher matcher = pattern.matcher(responseBody);
        if (matcher.find()) return matcher.group(1);
        return null;
    }

}
