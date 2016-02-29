package com.xmartlabs.template.ui.repo.detail;

import android.support.annotation.NonNull;

import com.f2prateek.dart.InjectExtra;
import com.xmartlabs.template.model.Repo;
import com.xmartlabs.template.ui.BaseFragment;
import com.xmartlabs.template.ui.SingleFragmentActivity;

public class RepoDetailActivity extends SingleFragmentActivity {
  @InjectExtra
  Repo repo;

  @NonNull
  @Override
  protected BaseFragment createFragment() {
    return new RepoDetailFragmentBuilder(repo).build();
  }
}
