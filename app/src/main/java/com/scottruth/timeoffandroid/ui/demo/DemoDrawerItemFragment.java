package com.scottruth.timeoffandroid.ui.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.scottruth.timeoffandroid.R;
import com.scottruth.timeoffandroid.ui.FragmentWithDrawer;

import butterknife.ButterKnife;

/**
 * Created by remer on 14/12/15.
 */
@FragmentWithArgs
public class DemoDrawerItemFragment extends FragmentWithDrawer {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_demo_drawer_item;
    }

    @Nullable
    @Override
    public String getTitle() {
        return getActivity().getString(R.string.public_repos_title);
    }
}
