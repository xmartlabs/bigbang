package com.xmartlabs.base.ui.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.xmartlabs.base.ui.BaseActivity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Activity that uses a {@link MvpPresenter} to implement the MVP pattern The activities that inherit from this class
 * will conform the V part of the said pattern.
 *
 * @param <V> the contract that provides the public API for the presenter to invoke view related methods
 * @param <P> the presenter that coordinates the view
 */
public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends BaseActivity implements MvpView {
  @Getter(AccessLevel.PROTECTED)
  @Setter(AccessLevel.PROTECTED)
  private P presenter;

  protected Context getContext() {
    return this;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = createPresenter();
    presenter.attachView(getView());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }

  /**
   * Retrieves this class instance as an MVP view (V).
   *
   * @return the MVP view
   */
  @NonNull
  @SuppressWarnings("unchecked")
  public V getView() {
    return (V) this;
  }

  /**
   * Creates the presenter instance.
   *
   * @return the created presenter instance
   */
  @NonNull
  protected abstract P createPresenter();
}
