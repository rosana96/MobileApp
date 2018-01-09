package com.example.rosana.booksapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rosana.booksapp.dialog.MailDialog;
import com.example.rosana.booksapp.dialog.NewChapterDialog;
import com.example.rosana.booksapp.repository.NovelsRepo;
import com.example.rosana.booksapp.model.Chapter;
import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.service.NovelsService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.createChooser;
import static android.view.View.VISIBLE;

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

    private static Novel novel;
    private List<Chapter> chapters = new ArrayList<>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    TextView textView;
    EditText editText;
    LinearLayout linearLayout;
    FloatingActionButton editBtn;
    FloatingActionButton mail_button;
    int currentChapterNr;
    FloatingActionButton nextChapterBtn;
    FloatingActionButton newChapterBtn;
    Toolbar appBarLayout;
    private DatabaseReference chaptersRef;
    private NovelsService novelsService;
    private boolean isAuthenticated;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentChapterNr = 0;
        isAuthenticated = Prefs.getBoolean("isAuthenticated", false);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            novel = NovelsRepo.findOne(getArguments().get(ARG_ITEM_ID).toString());

            Activity activity = this.getActivity();
            activity.setTitle(novel.getTitle());

            novelsService = ((BooksApplication) getActivity().getApplication()).getNovelsService();
            chaptersRef = ((BooksApplication) getActivity().getApplication()).getChaptersRef();
            chaptersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    novelsService.reloadChapters(dataSnapshot);
                    chapters = NovelsRepo.getChaptersOfNovel(novel);
                    textView.setText(getCurrentChapter(novel).getContent());
                    ItemDetailFragment.this.updateVisibility();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("ItemListActivity", "database connection error");
                    Toast.makeText(getActivity(), "Could not connect to database", Toast.LENGTH_LONG).show();
                }
            });

            appBarLayout = (Toolbar) activity.findViewById(R.id.detail_toolbar);
            if (appBarLayout != null) {
                appBarLayout.setTitle(novel.getTitle());
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.item_detail, container, false);
        textView = (TextView) rootView.findViewById(R.id.item_detail);
        editText = (EditText) rootView.findViewById(R.id.item_detail_edit);
        linearLayout = rootView.findViewById(R.id.linear_layout);

        nextChapterBtn = rootView.findViewById(R.id.nextChapterBtn);
        newChapterBtn = rootView.findViewById(R.id.newChapterBtn);
        updateVisibility();
        // Show the dummy content as text in a TextView.
        if (novel != null) {
//            textView.setText(getCurrentChapter(novel).getContent());
            editText.setVisibility(View.GONE);
        }

        editBtn = (FloatingActionButton) rootView.findViewById(R.id.fab);
        editBtn.setVisibility(isAuthenticated ? View.VISIBLE : View.GONE );
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You can now edit the content", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                textView.setVisibility(View.GONE);
                editText.setVisibility(VISIBLE);
                editText.setText(getCurrentChapter(novel).getContent());
//                fab.setVisibility(View.GONE);
                editText.requestFocus();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getVisibility()== VISIBLE) {
                    String cont = editText.getText().toString();
                    textView.setText(cont);
                    textView.setVisibility(VISIBLE);
                    editText.setVisibility(View.GONE);
                    Chapter newChapter = getCurrentChapter(novel);
                    newChapter.setContent(cont);
                    novelsService.updateChapter(newChapter);
                    NovelsRepo.updateChapter(newChapter);
                }
            }
        });

        nextChapterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNextChapter(novel);
            }
        });

        newChapterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentChapterNr += 1;
                NewChapterDialog newChapterDialog = new NewChapterDialog();
                newChapterDialog.generateDialog(getContext(), ItemDetailFragment.this);

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

        mail_button = rootView.findViewById(R.id.mail_button);
        mail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MailDialog mailDialog = new MailDialog();
                mailDialog.generateDialog(getContext(), novel, getActivity());
            }
        });

        return rootView;
    }

    public void addChapterToNovel(String chName) {
        Log.d("ITEM_DETAIL_FRAGMENT", "chapterNameReceived");
        String chapterName = Prefs.getString("newChapterName","Unnamed chapter");
        novelsService.generateChapterForNovel(novel, chapterName);
//                goToNextChapter(novel);
    }


    private Chapter getCurrentChapter(Novel novel) {
        // TODO change this later

        try {
            Chapter ch = chapters.get(currentChapterNr);
            return ch!=null ? ch : new Chapter();
        }
        catch (IndexOutOfBoundsException ex) {
            Log.d("ITEM_DETAIL", "This is the last chapter");
            if (currentChapterNr > 0) {
                currentChapterNr -= 1;
                return getCurrentChapter(novel);
            }
            return new Chapter();
        }
    }

    private void goToNextChapter(Novel novel) {
        currentChapterNr += 1;
        textView.setText(getCurrentChapter(novel).getContent());
        updateVisibility();
    }

    private void updateVisibility() {
        newChapterBtn.setVisibility(currentChapterNr == chapters.size()-1 && isAuthenticated? View.VISIBLE : View.GONE );
        nextChapterBtn.setVisibility(currentChapterNr == chapters.size()-1 ? View.GONE : View.VISIBLE );
        appBarLayout.setTitle(novel.getTitle() + " - " + getCurrentChapter(novel).getName());
    }
}


