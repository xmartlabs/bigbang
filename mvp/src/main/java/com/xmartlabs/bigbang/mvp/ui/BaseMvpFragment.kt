package com.xmartlabs.bigbang.mvp.ui

import android.os.Bundle
import android.view.View

import com.xmartlabs.bigbang.ui.BaseFragment

/**
 * Fragment that uses a [MvpPresenter] to implement the MVP pattern. The fragments that inherit from this class
 * will conform the V part of the said pattern.
 *
 * @param <V> the contract that provides the public API for the presenter to invoke view related methods
 * *
 * @param <P> the presenter that coordinates the view
 */
abstract class BaseMvpFragment<in V : MvpView, P : MvpPresenter<V>> : BaseFragment(), MvpView {
  abstract protected var presenter: P
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    @Suppress("UNCHECKED_CAST")
    presenter.attachView(this as V)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    presenter.detachView()
  }
}
