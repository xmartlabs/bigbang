package com.xmartlabs.template.ui.login

import com.xmartlabs.template.ui.common.TemplatePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor() : TemplatePresenter<LoginView>() {
  //TODO: Do something on login button clicked
  fun loginButtonClicked() = view?.gotoRecyclerExampleActivity()

  private fun setIsLoading(loading: Boolean) = view?.setIsLoading(loading)
}
