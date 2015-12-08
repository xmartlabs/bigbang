package com.scottruth.timeoffandroid.ui;

import android.support.annotation.NonNull;

public class MainActivity extends SingleFragmentActivity {
    @NonNull
    @Override
    protected BaseFragment createFragment() {
        return new MainFragmentBuilder("asd").build();
    }
}
