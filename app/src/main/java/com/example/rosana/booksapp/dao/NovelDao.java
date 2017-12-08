package com.example.rosana.booksapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rosana.booksapp.model.Novel;

import java.util.List;

/**
 * Created by rosana on 08.12.2017.
 */

@Dao
public interface NovelDao {

    @Query("SELECT * FROM Novels")
    List<Novel> getAll();

    @Query("SELECT * FROM Novels WHERE title = :title LIMIT 1")
    Novel findByTitle(String title);

    @Query("SELECT * FROM Novels WHERE id = :id LIMIT 1")
    Novel findById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Novel> Novels);

    @Insert
    void insertOne(Novel Novel);

    @Update
    void update(Novel Novel);

    @Delete
    void delete(Novel Novel);

    @Query("DELETE FROM Novels WHERE id = :id")
    void delete(String id);
}