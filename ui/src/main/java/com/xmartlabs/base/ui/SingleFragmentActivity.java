package com.xmartlabs.base.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * An extension of {@link BaseAppCompatActivity} that holds a single {@link Fragment}.
 *
 * The fragment is to be created through {@link #createFragment()} method, and will be added in the
 * {@link #onCreate(Bundle)} lifecycle method.
 */
public abstract class SingleFragmentActivity extends BaseAppCompatActivity {
  /**
   * Creates the {@link Fragment} held and shown by this Activity.
   * @return the Fragment instance to be shown
   */
  @NonNull
  protected abstract BaseFragment createFragment();

  /**
   * Specifies the layout of the Activity.
   * When overriding this method, be sure to include a container view in the layout with the id {@code fragment_container},
   * otherwise the Activity won't be able to add the Fragment in a proper way.
   * @return the layout resource id of the Activity
   */
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

  /**
   * Retrieves the currently held Fragment.
   * @return the Fragment held by this Activity
   */
  @Nullable
  protected Fragment getSingleFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    return fragmentManager.findFragmentById(R.id.fragment_container);
  }
}
