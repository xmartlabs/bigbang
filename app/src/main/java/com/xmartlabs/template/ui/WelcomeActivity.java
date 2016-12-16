package com.xmartlabs.template.ui;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.template.ui.mvp.BaseMvpFragment;

/**
 * Created by santiago on 31/08/15.
 */
@HensonNavigable
public class WelcomeActivity extends SingleFragmentActivity {
  @NonNull
  @Override
  protected BaseMvpFragment createFragment() {
    return new WelcomeFragmentBuilder().build();
  }
}
