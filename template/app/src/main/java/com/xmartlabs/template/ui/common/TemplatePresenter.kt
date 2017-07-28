package com.xmartlabs.template.ui.common

import com.xmartlabs.bigbang.ui.mvp.BaseMvpPresenter

/**
 * Created by diegomedina24 on 27/7/17.
 */
abstract class TemplatePresenter<T: TemplateView>: BaseMvpPresenter<T>() {
  override fun attachView(view: T) = view.setup()
}
