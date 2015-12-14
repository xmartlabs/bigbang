package com.scottruth.timeoffandroid.ui.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.scottruth.timeoffandroid.R;
import com.scottruth.timeoffandroid.controller.demo.DemoController;
import com.scottruth.timeoffandroid.model.demo.DemoRepo;
import com.scottruth.timeoffandroid.ui.FragmentWithDrawer;
import com.scottruth.timeoffandroid.ui.Henson;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import timber.log.Timber;


// TODO: Just for demo purposes, delete this class in a real project

@FragmentWithArgs
public class ReposListFragment extends FragmentWithDrawer {
    @Inject
    DemoController demoController;

    @Bind(R.id.filter_editText)
    EditText filterEditText;
    @Bind(R.id.repos_listView)
    ListView reposListView;

    private DemoAdapter reposAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        reposAdapter = new DemoAdapter();
        reposListView.setAdapter(reposAdapter);

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
        return getActivity().getString(R.string.public_repos_title);
    }

    @OnTextChanged(R.id.filter_editText)
    @SuppressWarnings("unused")
    void onFilterChanged(CharSequence filter, int int1, int int2, int int3) {
        filterRepositories(filter == null ? null : filter.toString());
    }

    @OnItemClick(R.id.repos_listView)
    @SuppressWarnings("unused")
    void onRepoSelected(AdapterView<?> parent, View view, int position, long id) {
        DemoRepo repo = reposAdapter.getItems().get(position);
        Intent intent = Henson.with(getActivity()).gotoRepoDetailActivity()
                .repo(repo)
                .build();
        startActivity(intent);
    }

    private void filterRepositories(@Nullable String filter) {
        demoController.getPublicRepositoriesFilteredBy(filter)
                .subscribe(
                        repos -> {
                            reposAdapter.setItems(repos);

                        },
                        error -> {
                            showAlertError(R.string.message_error_service_call);
                            Timber.d(error, "Error on service call");
                        }
                );
    }
}
