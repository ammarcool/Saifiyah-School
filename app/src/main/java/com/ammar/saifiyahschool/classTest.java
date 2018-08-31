package com.ammar.saifiyahschool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hookedonplay.decoviewlib.DecoView;

import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.ArrayList;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class classTest extends AppCompatActivity {


    private ArrayList<classTestData> classTestDataList = new ArrayList<>();
    private RecyclerView classTestRecyclerView;
    private classTestAdapter classTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test);

        classTestRecyclerView = findViewById(R.id.classTestRecycler);

        classTestAdapter = new classTestAdapter(this, classTestDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        classTestRecyclerView.setLayoutManager(mLayoutManager);
        classTestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        classTestRecyclerView.setAdapter(classTestAdapter);

        addClassTestMarks();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar Title");

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Class Test");

//        //font
//        myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/book.ttf");
////        myCustomFont= getResources().getFont(R.font.book);
//        testMarks.setTypeface(myCustomFont);
//        totalMarks.setTypeface(myCustomFont);

    }

    private void addClassTestMarks() {

        classTestData classTestData = new classTestData("English", "Dec", 4, "Ammar Miyaji", 80, 80);
        classTestDataList.add(classTestData);

        classTestData = new classTestData("Maths", "jan", 8, "Vivek bhatt", 10, 25);
        classTestDataList.add(classTestData);

        classTestAdapter.notifyDataSetChanged();
    }


}



