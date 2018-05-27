package com.xmartlabs.bigbang.mvp.ui

import android.os.Bundle

import com.xmartlabs.bigbang.mvp.BaseAppCompatActivity

/**
 * AppCompactActivity that uses a [MvpPresenter] to implement the MVP pattern. The activities that inherit from
 * this class will conform the V part of the said pattern.
 *
 * @param <V> the contract that provides the public API for the presenter to invoke view related methods
 * *
 * @param <P> the presenter that coordinates the view
 */
abstract class BaseMvpAppCompatActivity<V : MvpView, P : MvpPresenter<V>> : BaseAppCompatActivity(), MvpView {
  abstract protected var presenter: P
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    presenter.attachView(view)
  }
  
  override fun onDestroy() {
    super.onDestroy()
    presenter.detachView()
  }
  
  /** Retrieves this class instance as an MVP view (V). */
  @Suppress("UNCHECKED_CAST")
  val view: V
    get() = this as V
}
