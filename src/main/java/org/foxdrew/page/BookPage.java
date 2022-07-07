package org.foxdrew.page;

import org.foxdrew.utils.Config;

import java.util.List;

public class BookPage extends AuthorPage{
    public BookPage(String url, String responseBody, String author) {
        super(url, responseBody, author);
    }

    public List<String> getPoemsHrefsFromBookBlock() {
        String strList = this.getFirstMatch(Config.Patterns.poemsListFromBook);
        if (strList == null) return null;
        return getPoemsHrefs(strList);
    }
}
