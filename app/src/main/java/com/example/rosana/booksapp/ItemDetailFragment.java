package com.example.rosana.booksapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rosana.booksapp.dummy.NovelsRepo;
import com.example.rosana.booksapp.model.Chapter;
import com.example.rosana.booksapp.model.Novel;

import static android.content.Intent.createChooser;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private static Novel novel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            novel = NovelsRepo.findOne(getArguments().get(ARG_ITEM_ID).toString());

            Activity activity = this.getActivity();
            activity.setTitle(novel.getTitle());

            Toolbar appBarLayout = (Toolbar) activity.findViewById(R.id.detail_toolbar);
            if (appBarLayout != null) {
                appBarLayout.setTitle(novel.getTitle());
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.item_detail, container, false);
        final TextView textView = (TextView) rootView.findViewById(R.id.item_detail);
        final EditText editText = (EditText) rootView.findViewById(R.id.item_detail_edit);
        final LinearLayout linearLayout = rootView.findViewById(R.id.linear_layout);
        // Show the dummy content as text in a TextView.
        if (novel != null) {
            textView.setText(getCurrentChapter(novel).getContent());
            editText.setVisibility(View.GONE);
        }

        final FloatingActionButton editBtn = (FloatingActionButton) rootView.findViewById(R.id.fab);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You can now edit the content", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                textView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                editText.setText(getCurrentChapter(novel).getContent());
//                fab.setVisibility(View.GONE);
                editText.requestFocus();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
//
//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if(!hasFocus) {
//                    String cont = editText.getText().toString();
//                    textView.setText(cont);
//                    textView.setVisibility(View.VISIBLE);
//                    editText.setVisibility(View.GONE);
//                    novel.setContent(cont);
//                }
//            }
//        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getVisibility()==View.VISIBLE) {
                    String cont = editText.getText().toString();
                    textView.setText(cont);
                    textView.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    Chapter newChapter = getCurrentChapter(novel);
                    newChapter.setContent(cont);
                    NovelsRepo.updateChapter(newChapter);
                }
            }
        });

//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    if (editText.getVisibility()==View.VISIBLE) {
//                        String cont = editText.getText().toString();
//                        textView.setText(cont);
//                        textView.setVisibility(View.VISIBLE);
//                        editText.setVisibility(View.GONE);
//                        novel.setContent(cont);
//                        NovelsRepo.updateNovel(novel);
//                    }
//                }
//            }
//        });


        FloatingActionButton mail_button = rootView.findViewById(R.id.mail_button);
        mail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MailDialog mailDialog = new MailDialog();
                mailDialog.GenerateDialog(getContext(), novel, getActivity());
            }
        });

        return rootView;
    }

    private Chapter getCurrentChapter(Novel novel) {
        // TODO change this later
        Chapter ch = NovelsRepo.getLastChapterOfNovel(novel);
        return ch!=null ? ch : new Chapter();
    }

}


