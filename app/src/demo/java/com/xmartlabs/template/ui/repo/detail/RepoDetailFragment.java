package com.xmartlabs.template.ui.repo.detail;

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
import com.xmartlabs.template.model.Repo;
import com.xmartlabs.template.service.RepoService;
import com.xmartlabs.template.ui.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;

@FragmentWithArgs
public class RepoDetailFragment extends BaseFragment {
  @Arg(bundler = ParcelerArgsBundler.class)
  Repo repo;

  @Inject
  RepoService service;

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
