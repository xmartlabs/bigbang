package com.scottruth.timeoffandroid.ui.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.scottruth.timeoffandroid.R;
import com.scottruth.timeoffandroid.model.demo.DemoRepo;
import com.scottruth.timeoffandroid.service.demo.DemoService;
import com.scottruth.timeoffandroid.ui.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;

// TODO: Just for demo purposes, delete this class in a real project

@FragmentWithArgs
public class RepoDetailFragment extends BaseFragment {
    @Arg(bundler = DemoRepoArgsBundler.class)
    DemoRepo repo;

    @Inject
    DemoService service;

    @Bind(R.id.name_textView)
    TextView nameTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        nameTextView.setText(repo.getName());

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_demo_repo_details;
    }
}
