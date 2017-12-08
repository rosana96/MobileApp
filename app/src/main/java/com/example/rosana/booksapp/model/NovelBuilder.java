package com.example.rosana.booksapp.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rosana on 15.11.2017.
 */

public class NovelBuilder {
    private String author;
    private String content;
    private String title;
    private Date creationDate;
    private int numberOfChapters;
    private boolean finished;
    private String id;

    public NovelBuilder() {
    }

    public NovelBuilder withDefaults() {
        author = "DefaultAuthor";
        title = "Beautiful New Novel " + id;
        content = "This is an auto-generated novel. Do you like it? It's beautiful, isn't it? :))";
        creationDate = Calendar.getInstance().getTime();
        numberOfChapters = 0;
        finished = false;
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

    public NovelBuilder withContent(String content) {
        this.content = content;
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

    public Novel build() {
        Novel novel = new Novel(id, author, title, content, creationDate, numberOfChapters,finished);
        return novel;
    }

}
