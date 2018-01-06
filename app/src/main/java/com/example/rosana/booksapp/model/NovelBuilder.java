package com.example.rosana.booksapp.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rosana on 15.11.2017.
 */

public class NovelBuilder {
    private String author;
    private String title;
    private Date creationDate;
    private int numberOfChapters;
    private boolean finished;
    private String id;
    private String genre;

    public NovelBuilder() {
    }

    public NovelBuilder withDefaults() {
        author = "Default Author";
        title = "Beautiful New Novel " + id;
        creationDate = Calendar.getInstance().getTime();
        numberOfChapters = 0;
        finished = false;
        genre = "";
        return this;
    }
    public NovelBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public NovelBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public NovelBuilder withCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public NovelBuilder withNumberOfChapters(int numberOfChapters) {
        this.numberOfChapters = numberOfChapters;
        return this;
    }

    public NovelBuilder withFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    public NovelBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public NovelBuilder withGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public Novel build() {
        return new Novel(id, author, title, creationDate, numberOfChapters,finished, genre);
    }

}
