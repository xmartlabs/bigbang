package com.xmartlabs.template.ui.recyclerfragmentexample;

import android.support.annotation.NonNull;

import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.bigbang.ui.SingleFragmentActivity;

@HensonNavigable
public class RecyclerExampleActivity extends SingleFragmentActivity {
  @NonNull
  @Override
  protected RecyclerExampleFragment createFragment() {
    return new RecyclerExampleFragmentBuilder().build();
  }
}
