package com.example.rosana.booksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.rosana.booksapp.repository.NovelsRepo;
import com.example.rosana.booksapp.model.Chapter;
import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.model.NovelBuilder;
import com.example.rosana.booksapp.service.NovelsService;
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
import com.example.rosana.booksapp.service.MyFirebaseMessagingService;

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

    private NovelsService novelsService;
    private DatabaseReference dRef;

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

        novelsService = ((BooksApplication) getApplication()).getNovelsService();
    }

    public void save(View view) {
        String title = editText.getText().toString();
        String genre = genres[np.getValue()];
        String author = Prefs.getString("personName", "anonymous");
        Novel newNovel = new NovelBuilder().withDefaults().withGenre(genre).withAuthor(author).withTitle(title).build();
        novelsService.saveNovel(newNovel);

        startActivity(new Intent(view.getContext(), ItemListActivity.class));
        finish();
    }

}
