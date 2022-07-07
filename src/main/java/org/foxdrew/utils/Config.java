package org.foxdrew.utils;

import java.util.regex.Pattern;

public class Config {
    public static class Patterns {
        public static final Pattern title = Pattern.compile("<h1>([\\s\\S]*?)</h1>");
        public static final Pattern author = Pattern.compile("<div class=\"titleauthor\"><em><a href=\".*\">([\\s\\S]*?)</a></em></div>");
        public static final Pattern id = Pattern.compile("Свидетельство\\s*о\\s*публикации\\s*([\\s\\S]*?)\\s*\\n");
        public static final Pattern poemText = Pattern.compile("(?<=<div class=\"text\">)([\\s\\S]*?)(?=</div>)");
        public static final Pattern htmlTags = Pattern.compile("(<.*>|&nbsp;|&quot;)");
        public static final Pattern imgHref = Pattern.compile("<span class=\\\"authorsphoto\\\"><img src=\\\"([\\s\\S]*?)\\\".*>");
        public static final Pattern cyrillicLetters = Pattern.compile("[^а-я|А-Я|a-z|A-Z|\\s|\\d]");
        public static final Pattern hrefFromListOfPoem = Pattern.compile("href=\\\"(.*)\\\"\\s");
        public static final Pattern poemsListFromBook = Pattern.compile("<ul\\s*type\\s*=\\s*\\\"square\\\"\\s*style\\s*=\\s*\\\".*?\\\">([\\s\\S]*?)</ul>");
        public static final Pattern booksData = Pattern.compile("<div\\s*ID=\\\"bookheader\\\">\\s*<a\\s*href=\\\"(.*?)\\\">(.*?)</a>");
        public static final Pattern listOfPoem = Pattern.compile("<h2>Произведения</h2>\\S*\\n*<ul [\\s\\S]*?>\\s*\\n*([\\s\\S]*?)</ul>");
    }
}
