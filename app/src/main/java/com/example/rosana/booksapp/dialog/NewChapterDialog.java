package com.example.rosana.booksapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.rosana.booksapp.ItemDetailFragment;
import com.example.rosana.booksapp.R;
import com.example.rosana.booksapp.model.Novel;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by rosana on 09.01.2018.
 */

public class NewChapterDialog extends DialogFragment {

    public void generateDialog(Context context, Fragment itemDetailFragment){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.chapter_dialog);
        final EditText nameInput = dialog.findViewById(R.id.chapterName);
        Button dialogButton = (Button) dialog.findViewById(R.id.nextButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chapterName = nameInput.getText().toString();
                Prefs.putString("newChapterName", chapterName);
                dialog.dismiss();
                Log.d("CHAPTER_DIALOG","dismimssed");
                ((ItemDetailFragment) itemDetailFragment).addChapterToNovel(chapterName);
            }
        });
        dialog.show();
    }
}