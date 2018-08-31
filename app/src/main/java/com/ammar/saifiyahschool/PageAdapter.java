package com.ammar.saifiyahschool;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {

    private int noOfTabs;
    private final List<Fragment> oneFregment = new ArrayList<>();
    private  final List<String> oneTitle = new ArrayList<>();

    public PageAdapter(FragmentManager fm,int noOfTabs) {

        super(fm);
        this.noOfTabs=noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new EnglishFreg();
            case 1:
                return new HindiFreg();
            case 2:
                return new MathsFreg();
            case 3:
                return new ScienceFreg();
            case 4:
                return new SSFreg();
            case 5:
                return new UrduFreg();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }

//    @Nullable
////    @Override
////    public CharSequence getPageTitle(int position) {
////        return oneTitle.get(position);
////    }

    public void AddFregment(Fragment fragment, String title){
        oneFregment.add(fragment);
        oneTitle.add(title);
    }
}
