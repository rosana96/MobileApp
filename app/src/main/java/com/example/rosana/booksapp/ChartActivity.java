package com.example.rosana.booksapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rosana.booksapp.repository.NovelsRepo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rosana on 08.12.2017.
 */

public class ChartActivity extends AppCompatActivity implements Observer {

    private String[] genres = new String[] { "Fiction", "Fantasy", "Crime", "Romance", "Children", "Biography" };

    @BindView(R.id.chart)
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        NovelsRepo.addObserver(this);

        List<PieEntry> entries = new ArrayList<>();
        for (String g: genres) {
            entries.add(new PieEntry(NovelsRepo.getNovelsWithGenre(g).size(), g));
        }

        final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                Color.rgb(127,127,127), Color.rgb(146,208,80),Color.rgb(79,129,189)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS)
            colors.add(c);

        PieDataSet set = new PieDataSet(entries, "Genre distribution");
        set.setColors(colors);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    @Override
    public void update() {
        finish();
        startActivity(getIntent());
    }
}
