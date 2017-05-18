package com.xmartlabs.template.ui.onboarding;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.bigbang.ui.BaseFragment;
import com.xmartlabs.bigbang.ui.SingleFragmentActivity;

@HensonNavigable
public class OnboardingActivity extends SingleFragmentActivity {
  @NonNull
  @Override
  protected BaseFragment createFragment() {
    return new OnboardingFragmentBuilder().build();
  }
}
