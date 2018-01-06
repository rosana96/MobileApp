package com.example.rosana.booksapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rosana.booksapp.dummy.NovelsRepo;
import com.example.rosana.booksapp.model.Novel;
import com.example.rosana.booksapp.model.NovelBuilder;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    public static final int MAX_TITLE_LENGTH = 35;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private boolean mTwoPane;
    private boolean isAuthenticated;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("novels");

    @BindView(R.id.btn_logOut)
    Button btnLogOut;
    @BindView(R.id.item_list)
    RecyclerView recyclerView;

    private SimpleItemRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);
        isAuthenticated = Prefs.getBoolean("isAuthenticated", false);

        recyclerView = findViewById(R.id.item_list);
        adapter = new SimpleItemRecyclerViewAdapter(NovelsRepo.getAll());
        NovelsRepo.addObserver(adapter);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        btnLogOut.setVisibility(isAuthenticated ? View.VISIBLE : View.GONE);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Novel> firebaseNovels = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
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
                        Novel n = new Novel(id,author,title,creationDate,numberOfChapters,finished,genre);
                        firebaseNovels.add(n);

                    }
                }
                NovelsRepo.clearNovelList();
                NovelsRepo.insertAll(firebaseNovels);
//                novels = firebaseNovels;
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ItemListActivity", "database connection error");
                Toast.makeText(ItemListActivity.this,"Could not connect to database", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        PopulateDbAsync populateDbAsync = new PopulateDbAsync(db);
//        populateDbAsync.doInBackground();
//        db.novelDao().insertAll(NovelsRepo.NOVELS);
        super.onDestroy();
    }

    @OnClick(R.id.btn_logOut)
    public void loginWithGoogle(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent intent = new Intent(ItemListActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    public void addNovel(View view) {
        Bundle bundle = new Bundle();
        startActivity(new Intent(this, CreateNovelActivity.class));
        finish();
    }

    public void seeChart(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ChartActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//        private final AppDatabase mDb;
//        PopulateDbAsync(AppDatabase db) {
//            mDb = db;
//        }
//        @Override
//        protected  Void doInBackground(final Void... params) {
//            mDb.novelDao().insertAll(NovelsRepo.NOVELS);
//            return null;
//        }
//    }
//
//    private static class GetDbAsync extends AsyncTask<Void, Void, List<Novel>> {
//        private final AppDatabase mDb;
//        GetDbAsync(AppDatabase db) {
//            mDb = db;
//        }
//        @Override
//        protected  List<Novel> doInBackground(final Void... params) {
//           return mDb.novelDao().getAll().getValue();
//        }
//    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>
            implements Observer  {

        private List<Novel> novels;

        public SimpleItemRecyclerViewAdapter(List<Novel> items) {
            novels = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = novels.get(position);
            String pos = (position+1)+"";
            holder.mIdView.setText(pos);
            String title = novels.get(position).getTitle();
            if (title.length() > MAX_TITLE_LENGTH) {
                title = title.substring(0,MAX_TITLE_LENGTH-3);
                title +="...";
            }

            holder.mContentView.setText(title);

            Button deleteButton = holder.mView.findViewById(R.id.removeBtn);
            deleteButton.setVisibility(isAuthenticated ? View.VISIBLE : View.GONE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = holder.getAdapterPosition();
                    Novel deletedNovel = novels.get(position);
                    NovelsRepo.deleteNovel(deletedNovel);
                    dRef.child(deletedNovel.getId()).removeValue();
//                    novels.remove(position);
//                    adapter.notifyItemRemoved(position);
                    Log.d("delete",Integer.toString(position));
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ItemDetailActivity.class);
                            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                            context.startActivity(intent);
                    }
                }
            });

            FloatingActionButton addBtn = (FloatingActionButton) findViewById(R.id.addBtn);
            addBtn.setVisibility(isAuthenticated ? View.VISIBLE : View.GONE);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CreateNovelActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return novels.size();
        }

        public void update() {
            novels = NovelsRepo.getAll();
            this.notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Novel mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
