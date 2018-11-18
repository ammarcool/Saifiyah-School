package com.ammar.saifiyahschool;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {

    private int nNumOfTabs;

    Context context;

    public DynamicFragmentAdapter(FragmentManager fm, int NumofTabs,Context context) {
        super(fm);
        this.nNumOfTabs = NumofTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(final int i) {

        Bundle b = new Bundle();
        b.putInt("position", i);
        Fragment frag = EnglishFreg.newInstance();
        frag.setArguments(b);
        return frag;

    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }
}
