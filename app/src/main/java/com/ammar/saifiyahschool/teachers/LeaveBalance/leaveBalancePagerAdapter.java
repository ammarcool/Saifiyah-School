package com.ammar.saifiyahschool.teachers.LeaveBalance;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ammar.saifiyahschool.R;
import com.ammar.saifiyahschool.teachers.AddClassTest.viewClassTest;

public class leaveBalancePagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    public leaveBalancePagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new addLeaveBalance() ;
            case 1:
                return new viewLeaveBalance();
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
                return context.getString(R.string.add_leaveBalance);
            case 1:
                return context.getString(R.string.view_leaveBalance);
            default:
                return null;
        }
    }

}
