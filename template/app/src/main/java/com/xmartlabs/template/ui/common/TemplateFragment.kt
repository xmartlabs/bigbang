package com.xmartlabs.template.ui.common

import android.support.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.xmartlabs.bigbang.ui.mvp.BaseMvpFragment
import com.xmartlabs.bigbang.ui.mvp.MvpPresenter
import com.xmartlabs.template.R
import io.reactivex.exceptions.CompositeException
import java.io.IOException
import java.util.concurrent.CancellationException

abstract class TemplateFragment<V : TemplateView, P : MvpPresenter<V>> : BaseMvpFragment<V, P>(), TemplateView {
  override val isViewAlive: Boolean
    get() = isAdded && activity != null

  override fun setup() = Unit

  override fun showError(message: Int, title: Int, buttonTitle: Int) {
    if (isViewAlive) {
      MaterialDialog.Builder(context)
          .title(title)
          .content(message)
          .positiveText(buttonTitle)
          .build()
          .show()
    }
  }

  override fun showError(error: Throwable, @StringRes message: Int?) {
    if (error is CancellationException) {
      return
    }

    var receiverError = error
    if (receiverError is CompositeException) {
      receiverError = receiverError.exceptions[0]
    }
    if (receiverError is IOException) {
      showError(R.string.check_your_internet_connection, R.string.no_internet_connection)
    } else if (message == null) {
      showError(R.string.error_service_call_generic)
    } else {
      showError(message)
    }
  }
}
