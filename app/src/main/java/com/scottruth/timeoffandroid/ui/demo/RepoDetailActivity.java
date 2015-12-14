package com.scottruth.timeoffandroid.ui.demo;

import android.support.annotation.NonNull;

import com.f2prateek.dart.InjectExtra;
import com.scottruth.timeoffandroid.model.demo.DemoRepo;
import com.scottruth.timeoffandroid.ui.BaseFragment;
import com.scottruth.timeoffandroid.ui.SingleFragmentActivity;

// TODO: Just for demo purposes, delete this class in a real project

public class RepoDetailActivity extends SingleFragmentActivity {
    @InjectExtra("repo")
    DemoRepo repo;

    @NonNull
    @Override
    protected BaseFragment createFragment() {
        return new RepoDetailFragmentBuilder(repo).build();
    }
}
