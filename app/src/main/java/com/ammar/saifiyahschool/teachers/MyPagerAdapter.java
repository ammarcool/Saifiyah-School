package com.ammar.saifiyahschool.teachers;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;

import com.ammar.saifiyahschool.R;

public class MyPagerAdapter  extends FragmentStatePagerAdapter{

     Context context;

    public MyPagerAdapter( Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new addClassTestMarks();
            case 1:
                return new viewClassTest();
                default:
                    return null;
        }


    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
               return context.getString(R.string.add_classtest);
            case 1:
               return context.getString(R.string.view_classtest);
               default:
                   return null;
        }
    }

}
