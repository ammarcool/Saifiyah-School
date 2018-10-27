package com.ammar.saifiyahschool.teachers.Progress;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ammar.saifiyahschool.R;
import com.ammar.saifiyahschool.teachers.LeaveBalance.addLeaveBalance;

public class progressPagerAdapter extends FragmentStatePagerAdapter {

    Context context;
    public progressPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new addProgress() ;
            case 1:
                return new viewProgress();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.add_progress);
            case 1:
                return context.getString(R.string.view_progress);
            default:
                return null;
        }
    }


}
