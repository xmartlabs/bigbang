package com.scottruth.timeoffandroid.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 07/09/15.
 */
public abstract class ListFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private final List<T> fragments = new ArrayList<>();

    public ListFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public List<T> getFragments() {
        return fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
