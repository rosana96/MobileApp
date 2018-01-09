package com.example.rosana.booksapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "chapters", foreignKeys = {
        @ForeignKey(entity = Novel.class,
                parentColumns = "id",
                childColumns = "novelId",
                onDelete = ForeignKey.CASCADE)}, indices = {
        @Index(value = "novelId")
})
public class Chapter {
    @PrimaryKey
    @NonNull
    private String id;
    private String novelId;
    private String name;
    private String content;

    public Chapter() { }

    public Chapter(@NonNull String id, String novelId, String name, String content) {
        this.novelId = novelId;
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNovelId() {
        return novelId;
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
