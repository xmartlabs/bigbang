package com.xmartlabs.base.ui.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.base.ui.BaseFragment;

import lombok.AccessLevel;
import lombok.Setter;

/**
 * Fragment that uses a {@link MvpPresenter} to implement the MVP pattern. The fragments that inherit from this class
 * will conform the V part of the said pattern.
 *
 * @param <V> the contract that provides the public API for the presenter to invoke view related methods
 * @param <P> the presenter that coordinates the view
 */
@FragmentWithArgs
public abstract class BaseMvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends BaseFragment
    implements MvpView {
  @Setter(AccessLevel.PROTECTED)
  private P presenter;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    presenter = createPresenter();
    //noinspection unchecked
    presenter.attachView((V) this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.detachView();
  }

  /**
   * Creates the presenter instance.
   *
   * @return the created presenter instance
   */
  @NonNull
  protected abstract P createPresenter();
}
