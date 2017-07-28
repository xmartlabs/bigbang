package com.xmartlabs.template.helper

import android.support.annotation.CheckResult
import android.support.annotation.StringRes

import com.xmartlabs.template.ui.common.TemplateView

import org.reactivestreams.Subscription

import java.lang.ref.WeakReference

import io.reactivex.FlowableSubscriber

open class GeneralFlowableSubscriber<T> constructor(templateView: TemplateView? = null) : FlowableSubscriber<T> {
  private val viewReference: WeakReference<TemplateView> = WeakReference<TemplateView>(templateView)

  override fun onSubscribe(subscription: Subscription) = subscription.request(maxNumberOfElements)

  override fun onNext(t: T) { }

  override fun onError(throwable: Throwable) {
    val view = viewReference.get()
    if (alertOnError(throwable) && view != null && view.isViewAlive) {
      view.showError(throwable, getErrorMessage(throwable))
    }
  }

  override fun onComplete() {}

  @StringRes
  open protected fun getErrorMessage(throwable: Throwable): Int? = null

  @CheckResult
  open protected fun alertOnError(throwable: Throwable) = true

  protected val maxNumberOfElements: Long
    get() = java.lang.Long.MAX_VALUE
}
