package org.foxdrew.page;

import org.foxdrew.utils.Config;

public class PoemPage extends Page{
    public PoemPage(String url, String responseBody) {
        super(url, responseBody);
    }

    public String getTitle() {
        return this.getFirstMatch(Config.Patterns.title);
    }

    public String getAuthorName() {
        return this.getFirstMatch(Config.Patterns.author);
    }

    public String getRegistrationId() {
        return this.getFirstMatch(Config.Patterns.id);
    }

    public String getPoemText() {
        return this.getFirstMatch(Config.Patterns.poemText);
    }

    public String getImageHref() {
        return this.getFirstMatch(Config.Patterns.imgHref);
    }
}
