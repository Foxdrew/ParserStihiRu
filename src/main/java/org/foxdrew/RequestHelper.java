package org.foxdrew;

import org.foxdrew.page.AuthorPage;
import org.foxdrew.page.BookPage;
import org.foxdrew.page.Page;
import org.foxdrew.page.PoemPage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHelper {
    private static final String host = "https://stihi.ru/";

    public static Page getPage(String relativePath) {
        String url = host + relativePath;
        String response = getRequest(url);
        return new Page(url, response);
    }

    public static AuthorPage getAuthorPage(String author) {
        String url = host + "avtor/" + author;
        String response = getRequest(url);
        return new AuthorPage(url, response, author);
    }

    public static BookPage getBookPage(String relativePath) {
        String url = host + relativePath;
        String response = getRequest(url);
        return new BookPage(url, response, "wtf");
    }

    public static PoemPage getPoemPage(String relativePath) {
        String url = host + relativePath;
        String response = getRequest(url);
        return new PoemPage(url, response);
    }

    public static BufferedImage getImage(String link) throws IOException {
        URL url = new URL(host + link);
        InputStream is = url.openStream();
        BufferedImage image = ImageIO.read(is);
        is.close();
        return image;
    }

    private static String getRequest(String link)  {
        String result;

        try {
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            try(InputStream inputStream = con.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "windows-1251");)
            {
                result = inputStreamReaderToString(inputStreamReader);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static String inputStreamReaderToString(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        int r;
        while ((r = inputStreamReader.read()) != -1) {
            stringBuilder.append((char) r);
        }

        return stringBuilder.toString();
    }
}
