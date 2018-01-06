package com.example.rosana.booksapp.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rosana.booksapp.model.Chapter;
import com.example.rosana.booksapp.model.Novel;

import java.util.List;

@Dao
public interface ChapterDao {
    @Query("SELECT * FROM chapters where novelId = :novelId")
    LiveData<List<Chapter>> loadChapters(String novelId);

    @Query("SELECT * FROM chapters where novelId = :novelId")
    List<Chapter> loadChaptersSync(String novelId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Chapter> novels);

    @Insert
    void insertOne(Chapter chapter);

    @Update
    void update(Chapter chapter);
}
