package com.ammar.saifiyahschool;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class Syllabus extends AppCompatActivity {

private RecyclerView myrecyclerView;
private LinearLayoutManager linearLayoutManager;
private DividerItemDecoration dividerItemDecoration;
private RecyclerView.Adapter myAdapter;
private ArrayList<String> syllabusDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

//        linearLayoutManager = new LinearLayoutManager(Syllabus.this,LinearLayoutManager.VERTICAL,false);
//        myrecyclerView.setLayoutManager(linearLayoutManager);
//
//        myrecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//
//
//        myrecyclerView.setAdapter(new syllabusAdapter());
//
//        SyllabusData syllabusData = new SyllabusData("Feb","1st Unit","hello guys",null);
//        syllabusDataList.add(String.valueOf(syllabusData));
//
//        syllabusAdapter syllabusAdapter = new syllabusAdapter(this,syllabusDataList);
//        myrecyclerView.setAdapter(syllabusAdapter);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Subjects Names");
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        TabItem english = findViewById(R.id.english);
        TabItem hindi = findViewById(R.id.hindi);
        TabItem maths = findViewById(R.id.maths);
        TabItem ss = findViewById(R.id.ss);
        TabItem urdu = findViewById(R.id.urdu);
        TabItem science = findViewById(R.id.science);
        ViewPager viewPager = findViewById(R.id.viewPage);


        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}
