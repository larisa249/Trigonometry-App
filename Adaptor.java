package com.example.trigo;

import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Adaptor extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 6;

    public Adaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return SinFragment.newInstance();
            case 2:
                return CosFragment.newInstance();
            case 3:
                return TgFragment.newInstance();
            case 4:
                return CtgFragment.newInstance();
            case 5:
                return GFragment.newInstance();
            default:
                return GFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}

