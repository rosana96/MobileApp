package com.example.rosana.booksapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rosana.booksapp.dummy.NovelsRepo;
import com.example.rosana.booksapp.model.Novel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rosana on 08.12.2017.
 */

public class ChartActivity extends AppCompatActivity {

    private String[] genres = new String[] { "Fiction", "Fantasy", "Crime", "Romance", "Children", "Biography" };

    @BindView(R.id.chart)
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

//        int smallerThanFive = 0;
//        for (Novel n : NovelsRepo.getAll()) {
//            if (n.getNumberOfChapters() < 5)
//                smallerThanFive++;
//        }
//
//        int greater = NovelsRepo.getAll().size() - smallerThanFive;
//
//        PieChart pieChart = findViewById(R.id.chart);
//        pieChart.setHoleRadius(5);
//
//
//
//        List<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(smallerThanFive, 0));
//        entries.add(new PieEntry(greater, 1));
//
//        PieDataSet dataset = new PieDataSet(entries,"# of novel with nr of chapters less/greater than 5");
//
//        String[] labels = new String[2];
//        labels[0]="<5";
//        labels[1]=">=5";
//
//
//        PieData data = new PieData();
//        data.setDataSet(dataset);
////        data.getDataSetLabels(labels);
//        pieChart.setData(data);




        List<PieEntry> entries = new ArrayList<>();
        for (String g: genres) {
            entries.add(new PieEntry(NovelsRepo.getNovelsWithGenre(g).size(), g));
        }

        final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                Color.rgb(127,127,127), Color.rgb(146,208,80),Color.rgb(79,129,189)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);

        PieDataSet set = new PieDataSet(entries, "Genre distribution");
        set.setColors(colors);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

    }
}
