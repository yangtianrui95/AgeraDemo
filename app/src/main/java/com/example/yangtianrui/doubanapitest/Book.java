package com.example.yangtianrui.doubanapitest;

import java.util.Arrays;

/**
 * Created by yangtianrui on 16-5-29.
 */
public class Book {
    private String title;
    private String alt_Title;
    private String[] author;
    private String publisher;

    public Book(String title, String alt_Title, String[] author, String publisher) {
        this.title = title;
        this.alt_Title = alt_Title;
        this.author = author;
        this.publisher = publisher;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt_Title() {
        return alt_Title;
    }

    public void setAlt_Title(String alt_Title) {
        this.alt_Title = alt_Title;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title +
                '}';
    }
}
