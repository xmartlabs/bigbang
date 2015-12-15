package com.scottruth.timeoffandroid.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.scottruth.timeoffandroid.R;

import butterknife.ButterKnife;

/**
 * Created by remer on 15/12/15.
 */
@FragmentWithArgs
public class DrawerHeaderFragment extends BaseFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_header, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
