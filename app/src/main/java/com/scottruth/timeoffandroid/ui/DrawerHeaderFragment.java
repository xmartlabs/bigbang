package com.scottruth.timeoffandroid.ui;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.scottruth.timeoffandroid.R;

/**
 * Created by remer on 15/12/15.
 */
@FragmentWithArgs
public class DrawerHeaderFragment extends BaseFragment
{
    @Override
    protected int getLayoutResId() {
        return R.layout.view_drawer_header;
    }
}
