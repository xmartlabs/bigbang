package com.xmartlabs.template.ui.demo;

import android.support.annotation.NonNull;

import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.template.model.demo.DemoRepo;
import com.xmartlabs.template.ui.BaseFragment;
import com.xmartlabs.template.ui.SingleFragmentActivity;

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
