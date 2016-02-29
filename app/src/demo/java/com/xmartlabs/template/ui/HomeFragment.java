package com.xmartlabs.template.ui;

import android.support.annotation.Nullable;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.R;
import com.xmartlabs.template.ui.FragmentWithDrawer;

/**
 * Created by remer on 14/12/15.
 */
@FragmentWithArgs
public class HomeFragment extends FragmentWithDrawer {
  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_home;
  }

  @Nullable
  @Override
  public String getTitle() {
    return BaseProjectApplication.getContext().getString(R.string.home);
  }
}
