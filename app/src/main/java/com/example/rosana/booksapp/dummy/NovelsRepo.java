package com.example.rosana.booksapp.dummy;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.rosana.booksapp.dao.AppDatabase;
import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.model.NovelBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static android.R.attr.max;

public class NovelsRepo {

    private static AppDatabase db;

    public NovelsRepo(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }
//    /**
//     * An array of sample (dummy) items.
//     */
//    public static List<Novel> NOVELS = new ArrayList<>();
//
//
//    /**
//     * A map of sample (dummy) items, by ID.
//     */
//    public static Map<String, Novel> NOVEL_MAP = new HashMap<>();

    private static int COUNT = 20;

//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addNovel(createDefaultNovel());
//        }
//    }

    public static void addNovel(Novel novel) {
        db.novelDao().insertOne(novel);
//        NOVELS.add(novel);
//        NOVEL_MAP.put(novel.getId(), novel);
    }

    public static void deleteNovel(Novel novel) {
        db.novelDao().delete(novel);
//        Novel n =  NOVEL_MAP.get(id) ;
//        NOVELS.remove(n);
//        NOVEL_MAP.remove(id);
    }

    public static List<Novel> getAll() {
        return db.novelDao().getAll();
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

    public static void setNovels(List<Novel> novels) {
//        NovelsRepo.NOVELS = new ArrayList<>();
//        NOVEL_MAP = new HashMap<>();
//        for(Novel n : novels) {
//            addNovel(n);
//        }
    }

    public static void delete(final String argItemId) {
        delete(argItemId);
    }

    public static Novel findOne(String id) {
        return db.novelDao().findById(id);
    }

    public static int maxID() {
        int max = 0;
        for (Novel n : getAll()) {
            int id = Integer.parseInt(n.getId());
            if (id > max)
                max = id;
        }
        return max;
    }
}
