package com.example.rosana.booksapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Novels")
public class Novel {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "creation_date")
    private Date creationDate;

    @ColumnInfo(name = "number_of_chapters")
    private int numberOfChapters;

    @ColumnInfo(name = "finished")
    private boolean finished;

    @ColumnInfo(name = "genre")
    private String genre;

    public Novel(@NonNull String id, String author, String title, String content, Date creationDate, int numberOfChapters, boolean finished, String genre) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.creationDate = creationDate;
        this.numberOfChapters = numberOfChapters;
        this.finished = finished;
        this.title = title;
        this.genre = genre;
    }

    @Ignore
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return content;
    }
}