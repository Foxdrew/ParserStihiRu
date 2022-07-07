package org.foxdrew.page;

import org.foxdrew.Book;
import org.foxdrew.RequestHelper;
import org.foxdrew.utils.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class AuthorPage extends Page {
    private final String author;
    public AuthorPage(String url, String responseBody, String author) {
        super(url, responseBody);
        this.author = author;
    }

    private List<String> getUnlistedPoemsHrefs() {
        String strList = this.getFirstMatch(Config.Patterns.listOfPoem);
        if (strList == null) return null;

        return getPoemsHrefs(strList);
    }

    public List<String> getAllUnlistedPoemsHrefs() {
        List<String> poemsHrefs = new ArrayList<>();

        int count = 0;
        while (true) {
            AuthorPage page = RequestHelper.getAuthorPage(author + "&s=" + count);
            List<String> poemsHrefsOnPage = page.getUnlistedPoemsHrefs();
            if (poemsHrefsOnPage == null) break;
            poemsHrefs.addAll(poemsHrefsOnPage);
            count += 50;
        }

        return poemsHrefs;
    }

    /*public static List<String> getListOfUnlistedPoemsHrefs (String name) {
        System.out.println("Getting unlisted poems");

        int count = 0;
        List<String> listOfPoemsHrefs = new ArrayList<>();

        while (true) {
            AuthorPage page = HttpHelper.getAuthorPage(name + "&s=" + count);
            List<String> poemsLinks = page.getUnlistedPoems();
            if (poemsLinks == null) break;
            listOfPoemsHrefs.addAll(poemsLinks);
            count += 50;
        }

        System.out.println("Found " + listOfPoemsHrefs.size() + " unlisted poems");

        return listOfPoemsHrefs;
    }*/

    protected final List<String> getPoemsHrefs(String listOfPoem) {
        List<String> hrefList = new ArrayList<>();
        Matcher matcher = Config.Patterns.hrefFromListOfPoem.matcher(listOfPoem);
        while (matcher.find()) {
            hrefList.add(matcher.group(1));
        }
        return hrefList;
    }

    public List<Book> getBooksList() {
        List<Book> books = null;
        Matcher matcher = Config.Patterns.booksData.matcher(responseBody);
        while (matcher.find()) {
            if (books == null) books = new ArrayList<>();
            books.add(new Book(matcher.group(2), matcher.group(1)));
        }

        return books;
    }
}
