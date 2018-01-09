package com.example.rosana.booksapp.service;

import com.example.rosana.booksapp.model.Chapter;
import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.repository.NovelsRepo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by rosana on 08.01.2018.
 */

public class NovelsService {

    private DatabaseReference dRef;
    private DatabaseReference chaptersRef;


    public NovelsService(DatabaseReference dRef, DatabaseReference chaptersRef) {
        this.chaptersRef = chaptersRef;
        this.dRef = dRef;
    }

    public void reloadNovels(DataSnapshot dataSnapshot) {
        List<Novel> firebaseNovels = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Novel n = ds.getValue(Novel.class);
            Map<String, Object> map = (Map<String, Object>) ds.getValue();
            String id = ds.getKey();
            if (map != null) {
                String title = map.get("title").toString();
                String genre = map.get("genre").toString();
                String author = map.get("author").toString();
                boolean finished = Boolean.parseBoolean(map.get("finished").toString());
                int numberOfChapters = Integer.parseInt(map.get("numberOfChapters").toString());
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
                Date creationDate = new Date();
                try {
                    creationDate = format.parse(map.get("creationDate").toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Novel n = new Novel(id, author, title, creationDate, numberOfChapters, finished, genre);
                firebaseNovels.add(n);

            }
        }
        NovelsRepo.clearNovelList();
        NovelsRepo.insertAll(firebaseNovels);
//                novels = firebaseNovels;
//                adapter.notifyDataSetChanged();
    }

    public void reloadChapters(DataSnapshot dataSnapshot) {
        List<Chapter> firebaseChapters = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Map<String, Object> map = (Map<String, Object>) ds.getValue();
            String id = ds.getKey();
            if (map != null) {
                String name = map.get("name").toString();
                String content = map.get("content").toString();
                String novelId = map.get("novelId").toString();

                Chapter ch = new Chapter(id, novelId, name, content);
                firebaseChapters.add(ch);

            }
        }
        NovelsRepo.clearChaptersList();
        NovelsRepo.insertAllChapters(firebaseChapters);
    }
    
    public void saveNovel(Novel newNovel) {
        String id = addNovelToRealtimeDB(newNovel);
        newNovel.setId(id);
        NovelsRepo.addNovel(newNovel);
        generateChapterForNovel(newNovel, "First chapter");
    }

    private String addNovelToRealtimeDB(Novel newNovel) {
        DatabaseReference novelsRef = dRef;
        String genId = novelsRef.push().getKey();
        novelsRef.child(genId).setValue(novelToMap(newNovel));
        MyFirebaseMessagingService.sendMessageToAll(genId);
        return genId;

    }

    public void generateChapterForNovel(Novel novel, String chapterName) {
        String genId = chaptersRef.push().getKey();
        Chapter chapter = new Chapter(genId, novel.getId(), chapterName, "Edit me");
        chaptersRef.child(genId).setValue(chapterToMap(chapter));
        NovelsRepo.addChapter(chapter);
    }

    private Map<String, Object> chapterToMap(Chapter chapter) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", chapter.getName());
        result.put("content", chapter.getContent());
        result.put("novelId", chapter.getNovelId());

        return result;
    }

    private Map<String, Object> novelToMap(Novel novel) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("author", novel.getAuthor());
        result.put("title", novel.getTitle());
        result.put("genre", novel.getGenre());
        result.put("finished", novel.isFinished());
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date date = novel.getCreationDate();
        String dateString = df.format(date);
        result.put("creationDate", dateString);
        result.put("numberOfChapters", novel.getNumberOfChapters());

        return result;
    }

    public void updateChapter(Chapter chapter) {
        chaptersRef.child(chapter.getId()).setValue(chapterToMap(chapter));
    }

}
