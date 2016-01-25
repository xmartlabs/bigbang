package com.xmartlabs.template.ui.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.R;
import com.xmartlabs.template.controller.demo.DemoController;
import com.xmartlabs.template.model.demo.DemoRepo;
import com.xmartlabs.template.ui.FragmentWithDrawer;
import com.xmartlabs.template.ui.Henson;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnTextChanged;
import timber.log.Timber;

// TODO: Just for demo purposes, delete this class in a real project

@FragmentWithArgs
public class ReposListFragment extends FragmentWithDrawer {
  @Inject
  DemoController demoController;

  @Bind(R.id.filter_editText)
  EditText filterEditText;
  @Bind(R.id.repos_recyclerView)
  RecyclerView reposRecyclerView;

  private DemoAdapter reposAdapter;

  @NonNull
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);

    reposAdapter = new DemoAdapter();
    reposAdapter.setListener((repo, repoView) -> {
      Intent intent = Henson.with(getActivity()).gotoRepoDetailActivity()
          .repo(repo)
          .build();
      startActivity(intent);
    });

    reposRecyclerView.setAdapter(reposAdapter);
    reposRecyclerView.setHasFixedSize(true);
    reposRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    String defaultFilter = filterEditText.getText().toString();
    filterRepositories(defaultFilter);

    return view;
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_demo_repos_list;
  }

  @Nullable
  @Override
  public String getTitle() {
    return BaseProjectApplication.getContext().getString(R.string.public_repos_title);
  }

  @OnTextChanged(R.id.filter_editText)
  @SuppressWarnings("unused")
  void onFilterChanged(CharSequence filter, int int1, int int2, int int3) {
    filterRepositories(filter == null ? null : filter.toString());
  }

  private void filterRepositories(@Nullable String filter) {
    demoController.getPublicRepositoriesFilteredBy(filter)
        .<List<DemoRepo>>compose(RxLifecycle.bindUntilFragmentEvent(lifecycle(), FragmentEvent.DESTROY_VIEW))
        .subscribe(
            reposAdapter::setItems,
            error -> {
              showAlertError(R.string.message_error_service_call);
              Timber.d(error, "Error on service call");
            }
        );
  }
}
