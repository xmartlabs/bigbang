package com.xmartlabs.template.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.f2prateek.dart.*;
import com.xmartlabs.template.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by santiago on 03/03/16.
 */
@HensonNavigable
public class MainActivity extends BaseAppCompatActivity<IView, BasePresenter<IView>> {
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
  protected BasePresenter<IView> createPresenter() {
    return new BasePresenter<>();
  }
}
