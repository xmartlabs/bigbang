package com.xmartlabs.template.ui.repo.list;

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
import com.xmartlabs.template.controller.RepoController;
import com.xmartlabs.template.ui.FragmentWithDrawer;
import com.xmartlabs.template.ui.Henson;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnTextChanged;
import timber.log.Timber;

@FragmentWithArgs
public class RepoListFragment extends FragmentWithDrawer {
  @Inject
  RepoController repoController;

  @Bind(R.id.filter_editText)
  EditText filterEditText;
  @Bind(R.id.repos_recyclerView)
  RecyclerView reposRecyclerView;

  private RepoListAdapter reposAdapter;

  @NonNull
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);

    reposAdapter = new RepoListAdapter();
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
    return R.layout.fragment_repo_list;
  }

  @Nullable
  @Override
  public String getTitle() {
    return BaseProjectApplication.getContext().getString(R.string.repo_list_title);
  }

  @OnTextChanged(R.id.filter_editText)
  @SuppressWarnings("unused")
  void onFilterChanged(CharSequence filter, int int1, int int2, int int3) {
    filterRepositories(filter == null ? null : filter.toString());
  }

  private void filterRepositories(@Nullable String filter) {
    repoController.getPublicRepositoriesFilteredBy(filter)
        .compose(RxLifecycle.bindUntilEvent(lifecycle(), FragmentEvent.DESTROY_VIEW))
        .subscribe(
            reposAdapter::setItems,
            error -> {
              showAlertError(R.string.message_error_service_call);
              Timber.d(error, "Error on service call");
            }
        );
  }
}
