package com.xmartlabs.template.ui;

import android.support.annotation.NonNull;

/**
 * Created by santiago on 31/08/15.
 */
@com.f2prateek.dart.Henson
public class WelcomeActivity extends SingleFragmentActivity {
  @NonNull
  @Override
  protected BaseFragment createFragment() {
    return new WelcomeFragmentBuilder().build();
  }
}
