package com.example.rosana.booksapp.dummy;

import android.app.Activity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.rosana.booksapp.MySubject;
import com.example.rosana.booksapp.dao.AppDatabase;
import com.example.rosana.booksapp.dao.NovelDao_Impl;
import com.example.rosana.booksapp.model.Chapter;
import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.model.NovelBuilder;
import java.util.List;

import javax.security.auth.Subject;

public class NovelsRepo extends MySubject {

    private static AppDatabase db;

    public NovelsRepo() {
    }
    public static void initializeDb(Context context){
        db = Room.databaseBuilder(context,
                AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static List<Chapter> getChaptersOfNovel(Novel novel) {
        return db.chapterDao().loadChaptersSync(novel.getId());
    }

    public static Chapter getLastChapterOfNovel(Novel novel) {
        List<Chapter> chapters = getChaptersOfNovel(novel);
        if (chapters.isEmpty())
            return null;
        return chapters.get(chapters.size()-1);
    }

    // NOVELS CRUD
    public static void addNovel(Novel novel) {
        try {
            db.novelDao().insertOne(novel);
        }
        catch (Exception e) {
            updateNovel(novel);
            e.printStackTrace();
        }
        finally {
            notifyObservers();
        }
    }

    public static void updateNovel(Novel novel) {
        db.novelDao().update(novel);
        notifyObservers();
    }

    public static void deleteNovel(Novel novel) {
        db.novelDao().delete(novel);
        notifyObservers();
    }

    public static List<Novel> getAll() {
        return db.novelDao().getAll();
    }

    public static Novel findOne(String id) {
        return db.novelDao().findById(id);
    }

    public static void insertAll(List<Novel> novels) {
        db.novelDao().insertAll(novels);
        notifyObservers();
    }

    public static void clearNovelList() {
        db.novelDao().deleteAll();
        notifyObservers();
    }

    public static List<Novel> getNovelsWithGenre(String genre) {
        return db.novelDao().getWithGenre(genre);
    }

    //CHAPTERS CRUD
    public static void addChapter(Chapter chapter) {
        db.chapterDao().insertOne(chapter);
    }

    public static void updateChapter(Chapter chapter) {
        db.chapterDao().update(chapter);
    }

    private static Novel createDefaultNovel() {
        return new NovelBuilder().withDefaults().build();

    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    public static void delete(final String argItemId) {
        delete(argItemId);
    }
    

    public static int maxID() {
        int max = 0;
        for (Novel n : getAll()) {
            try {
                int id = Integer.parseInt(n.getId());
                if (id > max)
                    max = id;
            }
            catch (NumberFormatException ignored) {

            }
        }
        return max;
    }
}
