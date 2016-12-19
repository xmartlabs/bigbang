package com.xmartlabs.template.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.xmartlabs.template.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by santiago on 31/08/15.
 */
public abstract class SingleFragmentWithToolbarActivity extends SingleFragmentActivity {
  @BindView(R.id.toolbar)
  Toolbar toolbar;

  protected int getLayoutResId() {
    return R.layout.activity_fragment_with_toolbar;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    if (!showNavigationIcon()) {
      toolbar.setNavigationIcon(null);
    }

    setSupportActionBar(toolbar);
  }

  @NonNull
  public Toolbar getToolbar() {
    return toolbar;
  }

  protected boolean showNavigationIcon() {
    return false;
  }
}
