package com.example.rosana.booksapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.rosana.booksapp.dao.AppDatabase;
import com.example.rosana.booksapp.dummy.NovelsRepo;
import com.example.rosana.booksapp.model.Novel;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private boolean mTwoPane;
    private NovelsRepo novelsRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

//        GetDbAsync getDbAsync = new GetDbAsync(db);
//        NovelsRepo.NOVELS = getDbAsync.doInBackground();


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        novelsRepo = new NovelsRepo(getApplicationContext());
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(NovelsRepo.getAll()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(NovelsRepo.getAll()));
    }

    @Override
    protected void onDestroy() {
//        PopulateDbAsync populateDbAsync = new PopulateDbAsync(db);
//        populateDbAsync.doInBackground();
//        db.novelDao().insertAll(NovelsRepo.NOVELS);
        super.onDestroy();
    }

    public void addNovel(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, CreateNovelActivity.class);
        context.startActivity(intent);
    }

    public void seeChart(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ChartActivity.class);
        context.startActivity(intent);
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
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Novel> novels;

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
            holder.mIdView.setText(novels.get(position).getId());
            holder.mContentView.setText(novels.get(position).getTitle());

            Button button = holder.mView.findViewById(R.id.removeBtn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Novel deleted = novels.get(position);
//                    NovelsRepo.deleteNovel(deleted.getId());
//                    db.novelDao().delete(deleted);
//                    db.novelDao().delete(deleted);
                    NovelsRepo.deleteNovel(novels.get(holder.getAdapterPosition()));
                    Log.d("delete",Integer.toString(holder.getAdapterPosition()));
                    View recyclerView = findViewById(R.id.item_list);
                    setupRecyclerView((RecyclerView)recyclerView);
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
