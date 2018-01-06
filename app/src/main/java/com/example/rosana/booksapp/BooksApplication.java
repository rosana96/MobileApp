package com.example.rosana.booksapp;

import android.app.Application;
import android.content.ContextWrapper;

import com.example.rosana.booksapp.dummy.NovelsRepo;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rosana on 03.01.2018.
 */

public class BooksApplication extends Application {
    private String[] genres = new String[] { "Fiction", "Fantasy", "Crime", "Romance", "Children", "Biography" };

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Prefs class
        NovelsRepo.initializeDb(getApplicationContext());

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
