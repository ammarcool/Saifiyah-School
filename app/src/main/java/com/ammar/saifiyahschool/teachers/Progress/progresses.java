package com.ammar.saifiyahschool.teachers.Progress;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ammar.saifiyahschool.R;

public class progresses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progresses);

        Toolbar toolbar = findViewById(R.id.progressToolbar);
        toolbar.setTitle("Add & View Progresses");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabLayout classTestTabs = findViewById(R.id.progressTabLayout);
        TabItem addClassTest = findViewById(R.id.addProgress);
        TabItem viewClassTest = findViewById(R.id.viewProgress);
        ViewPager myviewPager = findViewById(R.id.progressviewPager);

        progressPagerAdapter pagerAdapter = new progressPagerAdapter(this,getSupportFragmentManager());
        myviewPager.setAdapter(pagerAdapter);
        classTestTabs.setupWithViewPager(myviewPager);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
