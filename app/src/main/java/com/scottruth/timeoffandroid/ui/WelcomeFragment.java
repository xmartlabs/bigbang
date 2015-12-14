package com.scottruth.timeoffandroid.ui;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.scottruth.timeoffandroid.R;

/**
 * Created by santiago on 31/08/15.
 */
@FragmentWithArgs
public class WelcomeFragment extends BaseFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }
}
