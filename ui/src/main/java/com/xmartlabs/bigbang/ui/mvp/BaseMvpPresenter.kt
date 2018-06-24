package com.xmartlabs.bigbang.ui.mvp

import android.support.annotation.UiThread

import java.lang.ref.WeakReference

/**
 * A base implementation of a [MvpPresenter] that uses a **WeakReference** for
 * referring to the attached view.
 *
 * Note that you should always check [.isViewAttached] before calling [.getView],
 * to protect against the case the view got detached before the code is executed.
 * This could be the case of any network related work, when the view gets detached between the call and the
 * server response.
 *
 * @param <V> the type of the [MvpView]
 */
open class BaseMvpPresenter<V : MvpView> : MvpPresenter<V> {
  private var viewReference: WeakReference<V>? = null
  
  /**
   * The attached view.
   */
  open val view
    @UiThread
    get() = viewReference?.get()
  
  override fun attachView(view: V) {
    viewReference = WeakReference(view)
  }

  override fun detachView() {
    viewReference?.let {
      it.clear()
      viewReference = null
    }
  }
}
