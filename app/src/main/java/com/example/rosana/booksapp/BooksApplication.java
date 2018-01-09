package com.example.rosana.booksapp;

import android.app.Application;
import android.content.ContextWrapper;

import com.example.rosana.booksapp.repository.NovelsRepo;
import com.example.rosana.booksapp.service.NovelsService;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by rosana on 03.01.2018.
 */

public class BooksApplication extends Application {
    private String[] genres = new String[] { "Fiction", "Fantasy", "Crime", "Romance", "Children", "Biography" };
    private NovelsService novelsService;
    private DatabaseReference novelsRef;
    private DatabaseReference chaptersRef;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Prefs class
        NovelsRepo.initializeDb(getApplicationContext());
        novelsRef = FirebaseDatabase.getInstance().getReference("novels");
        chaptersRef = FirebaseDatabase.getInstance().getReference("chapters");
        novelsService = new NovelsService(novelsRef, chaptersRef);

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

    }

    public NovelsService getNovelsService() {
        return novelsService;
    }

    public void setNovelsService(NovelsService novelsService) {
        this.novelsService = novelsService;
    }

    public DatabaseReference getNovelsRef() {
        return novelsRef;
    }

    public void setNovelsRef(DatabaseReference novelsRef) {
        this.novelsRef = novelsRef;
    }

    public DatabaseReference getChaptersRef() {
        return chaptersRef;
    }

    public void setChaptersRef(DatabaseReference chaptersRef) {
        this.chaptersRef = chaptersRef;
    }
}
