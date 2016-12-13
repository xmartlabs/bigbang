package com.xmartlabs.template.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.f2prateek.dart.Dart;
import com.trello.rxlifecycle.components.RxActivity;
import com.xmartlabs.template.BaseProjectApplication;

/**
 * Activity that uses a {@link IPresenter} to implement the MVP pattern
 * The activities that inherit from this class will conform the V part of the said pattern
 * @param <V> The contract that provides the public API for the presenter to invoke view related methods
 * @param <P> The presenter that coordinates the view
 */
public abstract class BaseActivity<V extends IView, P extends IPresenter<V>> extends RxActivity implements IView {
  private P presenter;
  protected Context getContext() {
    return this;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Dart.inject(this);
    BaseProjectApplication.getContext().inject(this);
    presenter = createPresenter();
    presenter.attachView(getView());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }

  /**
   * Returns the presenter instance
   * @return the presenter instance
   */
  public P getPresenter() {
    return presenter;
  }

  /**
   * Sets the presenter instance
   * @param presenter the presenter instance. It cannot be null.
   */
  public void setPresenter(@NonNull P presenter) {
    this.presenter = presenter;
  }

  /**
   * Retrieves this class instance as an MVP view (V)
   * @return the MVP view
   */
  @NonNull
  @SuppressWarnings("unchecked")
  public V getView() {
    return (V) this;
  }

  /**
   * Creates the presenter instance
   *
   * @return the created presenter instance
   */
  @NonNull
  protected abstract P createPresenter();
}
