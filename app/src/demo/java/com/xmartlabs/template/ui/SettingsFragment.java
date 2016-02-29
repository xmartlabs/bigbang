package com.xmartlabs.template.ui;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.template.R;

/**
 * Created by santiago on 10/09/15.
 */
@FragmentWithArgs
public class SettingsFragment extends ValidatableFragment {
  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_settings;
  }
}
