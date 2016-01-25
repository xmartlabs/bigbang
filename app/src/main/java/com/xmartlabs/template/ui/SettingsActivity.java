package com.xmartlabs.template.ui;

import android.support.annotation.NonNull;

/**
 * Created by santiago on 10/09/15.
 */
@com.f2prateek.dart.Henson
public class SettingsActivity extends SingleFragmentWithToolbarActivity {
  @NonNull
  @Override
  protected BaseFragment createFragment() {
    return new SettingsFragmentBuilder().build();
  }

  @Override
  protected boolean showNavigationIcon() {
    return true;
  }
}
