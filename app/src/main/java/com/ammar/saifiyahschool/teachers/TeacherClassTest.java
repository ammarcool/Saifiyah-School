package com.ammar.saifiyahschool.teachers;

import android.content.Context;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ammar.saifiyahschool.R;


public class TeacherClassTest extends AppCompatActivity {

Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_test);

        Toolbar toolbar = findViewById(R.id.classTesttoolbar);
        toolbar.setTitle("Add & View Class-Test");
        setSupportActionBar(toolbar);

        TabLayout classTestTabs = findViewById(R.id.classTestTabs);
        TabItem addClassTest = findViewById(R.id.addClassTest);
        TabItem viewClassTest = findViewById(R.id.viewClassTest);
        ViewPager myviewPager = findViewById(R.id.classTestviewPage);

        PagerAdapter pagerAdapter = new MyPagerAdapter(this,getSupportFragmentManager());
        myviewPager.setAdapter(pagerAdapter);
        classTestTabs.setupWithViewPager(myviewPager);


    }


}
