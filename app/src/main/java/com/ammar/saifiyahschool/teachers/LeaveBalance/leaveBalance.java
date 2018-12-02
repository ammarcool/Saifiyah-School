package com.ammar.saifiyahschool.teachers.LeaveBalance;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ammar.saifiyahschool.R;
import com.ammar.saifiyahschool.teachers.AddClassTest.MyPagerAdapter;

public class leaveBalance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_balance);

        Toolbar toolbar = findViewById(R.id.leaveBalanceToolbar);
        toolbar.setTitle("Add & View Leave");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabLayout classTestTabs = findViewById(R.id.leaveBalanceTabLayout);
        TabItem addClassTest = findViewById(R.id.addLeaveBalance);
        TabItem viewClassTest = findViewById(R.id.viewLeaveBalance);
        ViewPager myviewPager = findViewById(R.id.leaveBalanceviewPager);

        leaveBalancePagerAdapter pagerAdapter = new leaveBalancePagerAdapter(this,getSupportFragmentManager());
        myviewPager.setAdapter(pagerAdapter);
        classTestTabs.setupWithViewPager(myviewPager);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    }

