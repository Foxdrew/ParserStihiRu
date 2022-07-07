package org.foxdrew;

public class Book {
    private String title;
    private String href;

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public Book(String title, String href) {
        this.title = title;
        this.href = href;
    }
}
