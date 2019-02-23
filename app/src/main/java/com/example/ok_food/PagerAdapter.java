package com.example.ok_food;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public int mNumOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment_makanan();
            case 1:
                return new fragment_minuman();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
