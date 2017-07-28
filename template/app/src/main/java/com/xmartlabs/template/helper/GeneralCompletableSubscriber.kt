package com.xmartlabs.template.helper

import android.support.annotation.CheckResult
import android.support.annotation.StringRes

import com.xmartlabs.template.ui.common.TemplateView

import java.lang.ref.WeakReference

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

open class GeneralCompletableSubscriber constructor(templateView: TemplateView? = null) : CompletableObserver {
  private val viewReference = WeakReference<TemplateView>(templateView)

  override fun onSubscribe(disposable: Disposable) { }

  override fun onComplete() { }

  override fun onError(throwable: Throwable) {
    val view = viewReference.get()
    if (alertOnError(throwable) && view != null && view.isViewAlive) {
      view.showError(throwable, getErrorMessage(throwable))
    }
  }

  @StringRes
  open protected fun getErrorMessage(throwable: Throwable): Int? = null

  @CheckResult
  open protected fun alertOnError(throwable: Throwable) = true
}
