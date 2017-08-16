package com.xmartlabs.template.helper

import android.support.annotation.CheckResult
import android.support.annotation.StringRes

import com.xmartlabs.template.ui.common.TemplateView

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

import java.lang.ref.WeakReference

open class GeneralObservableSubscriber<T> constructor(templateView: TemplateView? = null) : Subscriber<T> {
  private val viewReference = WeakReference<TemplateView>(templateView)

  override fun onSubscribe(subscription: Subscription) { }

  override fun onNext(t: T) { }

  override fun onError(throwable: Throwable) {
    val view = viewReference.get()
    if (alertOnError(throwable) && view != null && view.isViewAlive) {
      view.showError(throwable, getErrorMessage(throwable))
    }
  }

  override fun onComplete() { }

  @StringRes
  protected open fun getErrorMessage(throwable: Throwable): Int? = null

  @CheckResult
  protected open fun alertOnError(throwable: Throwable) = true
}
