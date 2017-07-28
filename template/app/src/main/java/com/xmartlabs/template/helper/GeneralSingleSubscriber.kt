package com.xmartlabs.template.helper

import android.support.annotation.CheckResult
import android.support.annotation.StringRes

import com.xmartlabs.template.ui.common.TemplateView

import java.lang.ref.WeakReference

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

open class GeneralSingleSubscriber<T> constructor(templateView: TemplateView? = null) : SingleObserver<T> {
  private val viewReference = WeakReference<TemplateView>(templateView)

  override fun onSubscribe(disposable: Disposable) { }

  override fun onError(throwable: Throwable) {
    val view = viewReference.get()
    if (alertOnError(throwable) && view != null && view.isViewAlive) {
      view.showError(throwable, getErrorMessage(throwable))
    }
  }

  override fun onSuccess(t: T) { }

  @StringRes
  open protected fun getErrorMessage(throwable: Throwable): Int? = null

  @CheckResult
  open protected fun alertOnError(throwable: Throwable) = true
}
