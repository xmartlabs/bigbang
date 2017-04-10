package com.xmartlabs.base.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/** {@link SingleFragmentActivity} with {@link Toolbar} support. */
public abstract class SingleFragmentWithToolbarActivity extends SingleFragmentActivity {
  @BindView(R2.id.toolbar)
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

  /**
   * Retrieves the {@link Toolbar}.
   *
   * @return the {@link Toolbar}
   */
  @NonNull
  public Toolbar getToolbar() {
    return toolbar;
  }

  /**
   * Used to instruct the {@link Toolbar} to show/hide the navigation icon.
   *
   * @return whether or not to show the navigation icon
   */
  protected boolean showNavigationIcon() {
    return false;
  }
}
