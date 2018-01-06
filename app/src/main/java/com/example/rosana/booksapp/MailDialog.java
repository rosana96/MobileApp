
package com.example.rosana.booksapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.rosana.booksapp.R;
import com.example.rosana.booksapp.model.Novel;

public class MailDialog extends DialogFragment {

    public void GenerateDialog(Context context, final Novel novel, final Activity activity){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.mail_dialog);
        final EditText nameInput = dialog.findViewById(R.id.namePerson);
        final EditText emailInput = dialog.findViewById(R.id.mailAddress);
        Button dialogButton = (Button) dialog.findViewById(R.id.sendButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message ="Dear " + nameInput.getText().toString() + ",\n";
                message += "You subscribed to this novel: \n";
                message += novel.getTitle();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailInput.getText().toString()});
                email.putExtra(Intent.EXTRA_SUBJECT, "Novel: " + novel.getTitle());
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("message/rfc822");
                Intent intent = Intent.createChooser(email, ((String) "Choose an Email client :"));
                activity.startActivity(intent);
                dialog.dismiss();

            }
        });
        dialog.show();
    }
}