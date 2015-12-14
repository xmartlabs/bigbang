package com.scottruth.timeoffandroid.ui;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.scottruth.timeoffandroid.R;

@FragmentWithArgs
public class MainFragment extends BaseFragment {
    @Arg
    String sampleArg;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }
}
