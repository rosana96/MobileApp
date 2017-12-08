package com.example.rosana.booksapp.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.rosana.booksapp.converter.DateConverter;
import com.example.rosana.booksapp.model.Novel;

/**
 * Created by rosana on 08.12.2017.
 */

@Database(entities = {Novel.class}, version = 3, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "novelsDB";

    public abstract NovelDao novelDao();
}