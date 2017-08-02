package com.xmartlabs.template.ui.common

import android.support.annotation.StringRes

import com.xmartlabs.bigbang.ui.mvp.MvpView
import com.xmartlabs.template.R

interface TemplateView : MvpView {
  fun setup()
  fun showError(@StringRes message: Int, @StringRes title: Int = R.string.error,
                @StringRes buttonTitle: Int = android.R.string.ok)
  fun showError(error: Throwable, @StringRes message: Int?)
  val isViewAlive: Boolean
}
