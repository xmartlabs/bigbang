package com.xmartlabs.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.base.ui.mvp.BaseMvpAppCompatActivity;
import com.xmartlabs.base.ui.mvp.BaseMvpPresenter;
import com.xmartlabs.base.ui.mvp.MvpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@HensonNavigable
public class MainActivity extends BaseMvpAppCompatActivity<MvpView, BaseMvpPresenter<MvpView>> {
    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @NonNull
    @Override
    protected BaseMvpPresenter<MvpView> createPresenter() {
        return new BaseMvpPresenter<>();
    }
}
