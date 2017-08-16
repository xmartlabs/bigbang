package com.xmartlabs.template.ui.common

import com.xmartlabs.bigbang.ui.mvp.BaseMvpPresenter

abstract class TemplatePresenter<T: TemplateView> : BaseMvpPresenter<T>() {
  override fun attachView(view: T) {
    super.attachView(view)
    view.setup()
  }
}
