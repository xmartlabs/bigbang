package com.xmartlabs.bigbang.mvp.ui

import android.app.Activity
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment

/**
 * The base interface that defines a presenter in the MVP patterns.
 *
 * @param <V> The view type to be attached to this presenter
 */
interface MvpPresenter<in V : MvpView> {
  /**
   * Sets the view to this presenter instance.
   *
   * @param view the view to be attached to this presenter
   */
  @UiThread
  fun attachView(view: V)

  /**
   * This method will be called when the view is destroyed.
   * If the view is an activity, it will be invoked from [Activity.onDestroy]
   * If the view is a fragment, it will be invoked from [Fragment.onDestroyView].
   */
  @UiThread
  fun detachView()
}
