package com.scottruth.timeoffandroid.ui;

import android.support.v4.app.FragmentManager;

import com.scottruth.timeoffandroid.ui.demo.DemoDrawerItemFragment;
import com.scottruth.timeoffandroid.ui.demo.DemoDrawerItemFragmentBuilder;
import com.scottruth.timeoffandroid.ui.demo.ReposListFragment;
import com.scottruth.timeoffandroid.ui.demo.ReposListFragmentBuilder;

/**
 * Created by santiago on 16/11/15.
 */
public class MainFragmentPagerAdapter extends ListFragmentPagerAdapter<FragmentWithDrawer> {
    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        DemoDrawerItemFragment demoDrawerItemFragment = new DemoDrawerItemFragmentBuilder().build();
        getFragments().add(demoDrawerItemFragment);

        ReposListFragment reposListFragment = new ReposListFragmentBuilder().build();
        getFragments().add(reposListFragment);
    }
}
