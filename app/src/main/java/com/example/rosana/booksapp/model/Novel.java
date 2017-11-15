package com.example.rosana.booksapp.model;

import java.io.Serializable;
import java.util.Date;

public class Novel {
    private String id;
    private String author;
    private String title;
    private String content;
    private Date creationDate;
    private int numberOfChapters;
    private boolean finished;

    public Novel(String id, String author, String title, String content, Date creationDate, int numberOfChapters, boolean finished) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.creationDate = creationDate;
        this.numberOfChapters = numberOfChapters;
        this.finished = finished;
        this.title = title;
    }

    public Novel() {
        this.author = "Default author";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getNumberOfChapters() {
        return numberOfChapters;
    }

    public void setNumberOfChapters(int numberOfChapters) {
        this.numberOfChapters = numberOfChapters;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return content;
    }
}