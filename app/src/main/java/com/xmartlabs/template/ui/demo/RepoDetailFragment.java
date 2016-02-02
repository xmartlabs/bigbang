package com.xmartlabs.template.ui.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.xmartlabs.template.R;
import com.xmartlabs.template.model.demo.DemoRepo;
import com.xmartlabs.template.service.demo.DemoService;
import com.xmartlabs.template.ui.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;

// TODO: Just for demo purposes, delete this class in a real project

@FragmentWithArgs
public class RepoDetailFragment extends BaseFragment {
  @Arg(bundler = ParcelerArgsBundler.class)
  DemoRepo repo;

  @Inject
  DemoService service;

  @Bind(R.id.name_textView)
  TextView nameTextView;

  @NonNull
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);

    nameTextView.setText(repo.getName());

    return view;
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_repo_details;
  }
}
