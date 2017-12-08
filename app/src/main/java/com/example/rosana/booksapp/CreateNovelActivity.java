package com.example.rosana.booksapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.rosana.booksapp.dummy.NovelsRepo;
import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.model.NovelBuilder;

/**
 * Created by rosana on 05.12.2017.
 */

public class CreateNovelActivity extends AppCompatActivity {
    EditText editText;
    NumberPicker np;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_novel);

        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        editText = (EditText) findViewById(R.id.titleBox);
        np = (NumberPicker) findViewById(R.id.chaptersNrPicker);
        np.setMaxValue(20);
        np.setMinValue(2);
        np.setWrapSelectorWheel(true);
        np.setValue(5);

    }

    public void save(View view) {
        String title = editText.getText().toString();
        int maxNrChapters = np.getValue();
        Novel newNovel = new NovelBuilder().withDefaults().withNumberOfChapters(maxNrChapters).withId(Integer.toString(NovelsRepo.maxID()+1)).withAuthor("username will be added here").withTitle(title).build();
        NovelsRepo.addNovel(newNovel);
        finish();

    }
}
