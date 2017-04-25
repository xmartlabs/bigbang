package com.xmartlabs.base.ui.mvp;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

/**
 * A base implementation of a {@link MvpPresenter} that uses a <b>WeakReference</b> for
 * referring to the attached view.
 *
 * Note that you should always check {@link #isViewAttached()} before calling {@link #getView()},
 * to protect against the case the view got detached before the code is executed.
 * This could be the case of any network related work, when the view gets detached between the call and the
 * server response.
 *
 * @param <V> the type of the {@link MvpView}
 */
public class BaseMvpPresenter<V extends MvpView> implements MvpPresenter<V> {
  private WeakReference<V> viewReference;

  @Override
  public void attachView(V view) {
    viewReference = new WeakReference<>(view);
  }

  @Override
  public void detachView() {
    if (viewReference != null) {
      viewReference.clear();
      viewReference = null;
    }
  }

  /**
   * Checks if the view is currently attached to the presenter.
   * This method should always be called before calling {@link #getView()}.
   *
   * @return whether the view is currently attached to the presenter
   */
  @UiThread
  public boolean isViewAttached() {
    return viewReference != null && viewReference.get() != null;
  }

  /**
   * Gets the attached view.
   *
   * @return the view instance, if attached. Otherwise, it returns <code>null</code>
   */
  @UiThread
  @Nullable
  public V getView() {
    return viewReference == null ? null : viewReference.get();
  }
}
