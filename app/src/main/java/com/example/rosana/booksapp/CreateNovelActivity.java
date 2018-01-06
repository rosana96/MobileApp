package com.example.rosana.booksapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.rosana.booksapp.dummy.NovelsRepo;
import com.example.rosana.booksapp.model.Chapter;
import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.model.NovelBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rosana on 05.12.2017.
 */

public class CreateNovelActivity extends AppCompatActivity {

    @BindView(R.id.titleBox)
    EditText editText;

    @BindView(R.id.chaptersNrPicker)
    NumberPicker np;

    @BindView(R.id.saveBtn)
    Button saveBtn;

    private DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("novels");

    private String[] genres = new String[] { "Fiction", "Fantasy", "Crime", "Romance", "Children", "Biography" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_novel);
        ButterKnife.bind(this);

        np.setMaxValue(5);
        np.setMinValue(0);
        np.setWrapSelectorWheel(true);
        np.setDisplayedValues(genres);

    }

    public void save(View view) {
        String title = editText.getText().toString();
        String genre = genres[np.getValue()];
//        String id = Integer.toString(NovelsRepo.maxID()+1);
        String author = Prefs.getString("personName", "anonymous");
        Novel newNovel = new NovelBuilder().withDefaults().withGenre(genre).withAuthor(author).withTitle(title).build();
        String id = addNovelToRealtimeDB(newNovel);
        newNovel.setId(id);
        NovelsRepo.addNovel(newNovel);

        generateFirstChapterForNovel(newNovel);
        startActivity(new Intent(view.getContext(), ItemListActivity.class));
        finish();
    }

    private String addNovelToRealtimeDB(Novel newNovel) {
        DatabaseReference novelsRef = dRef;
        String genId = novelsRef.push().getKey();
        novelsRef.child(genId).setValue(novelToMap(newNovel));
//        novelsRef.push().setValue(newNovel);
//        DatabaseReference pushedPostRef = novelsRef.push();
//        String postId = pushedPostRef.getKey();
        return genId;

    }

    private void generateFirstChapterForNovel(Novel novel) {
        Chapter chapter = new Chapter(novel.getId(),"First chapter","some nice beginning of a story");
        Novel novel1 = NovelsRepo.findOne(novel.getId());
        NovelsRepo.addChapter(chapter);
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
        result.put("creationDate",dateString);
        result.put("numberOfChapters", novel.getNumberOfChapters());

        return result;
    }
}
