package com.xmartlabs.template.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.xmartlabs.template.R;
import com.xmartlabs.template.ui.mvp.BaseMvpFragment;

/**
 * Created by santiago on 31/08/15.
 */
public abstract class SingleFragmentActivity extends BaseAppCompatActivity {
  @NonNull
  protected abstract BaseMvpFragment createFragment();

  @LayoutRes
  protected int getLayoutResId() {
    return R.layout.activity_fragment;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(getLayoutResId());

    Fragment fragment = getSingleFragment();
    if (fragment == null) {
      fragment = createFragment();
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.fragment_container, fragment)
          .commit();
    }
  }

  @Nullable
  protected Fragment getSingleFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    return fragmentManager.findFragmentById(R.id.fragment_container);
  }
}
