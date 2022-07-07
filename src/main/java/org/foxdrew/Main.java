package org.foxdrew;

import org.foxdrew.page.AuthorPage;
import org.foxdrew.page.BookPage;
import org.foxdrew.utils.Config;
import org.foxdrew.utils.Tuple2;
import org.foxdrew.utils.UserInput;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String author = UserInput.getAuthor();
        AuthorPage authorPage = RequestHelper.getAuthorPage(author);
        List<String> unlistedPoemsHrefs = authorPage.getAllUnlistedPoemsHrefs();
        System.out.println("Getting all poems links...");
        List<Book> books = authorPage.getBooksList();
        List<Tuple2<String, List<String>>> booksNamesWithPoemsHrefs = new ArrayList<>();

        for (Book book : books) {
            BookPage bookPage = RequestHelper.getBookPage(book.getHref());
            Tuple2<String, List<String>> bookWithPoemsHrefs = new Tuple2<>(book.getTitle(), bookPage.getPoemsHrefsFromBookBlock());
            booksNamesWithPoemsHrefs.add(bookWithPoemsHrefs);
        }

        int count = 0;
        for (var kek : booksNamesWithPoemsHrefs) {
            count += kek.getSecond().size();
        }

        count += unlistedPoemsHrefs.size();
        System.out.println("Found " + count + " poems");

        String mainPath = "./stihiRuBackup";
        File mainDir = new File(mainPath);
        if (!mainDir.exists()) mainDir.mkdirs();

        File authorDir = new File(mainDir, author);
        if (!authorDir.exists()) authorDir.mkdirs();

        File unlistedDir = new File(authorDir, "0. Не вошедшие в зборники");
        if (!unlistedDir.exists()) unlistedDir.mkdirs();

        int unlistedCounter = 0;
        for (String href : unlistedPoemsHrefs) {
            ++unlistedCounter;
            getAndSavePoem(href, unlistedDir, unlistedCounter);
            System.out.println("Downloaded and saved: " + unlistedCounter + "/" + count);
        }


        for (var tuple: booksNamesWithPoemsHrefs) {
            String normalisedDirectoryName = tuple.getFirst().replaceAll(Config.Patterns.cyrillicLetters.toString(), "");
            File bookDirectory = new File(authorDir, normalisedDirectoryName);
            if (!bookDirectory.exists()) bookDirectory.mkdirs();
            int inDirectoryCount = 0;
            for (String href : tuple.getSecond()) {
                ++inDirectoryCount;
                ++unlistedCounter;
                getAndSavePoem(href, bookDirectory, inDirectoryCount);
                System.out.println("Downloaded and saved: " + unlistedCounter + "/" + count);
            }

        }

        System.out.println("Finished!");

    }


    public static void saveToFile(Poem poem, String path) {
        try(FileOutputStream out = new FileOutputStream(path + ".docx"))
        {
            poem.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAndSavePoem(String href, File directory, int counter) throws IOException {
        Poem poem = Poem.parse(RequestHelper.getPoemPage(href));
        String normalisedName = poem.getTitle().replaceAll(Config.Patterns.cyrillicLetters.toString(), "");
        String path = directory.getPath() + "/" + counter + ". " + normalisedName;
        saveToFile(poem, path);
    }
}