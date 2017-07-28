package com.xmartlabs.template.ui.login

import com.annimon.stream.Optional
import com.xmartlabs.bigbang.core.Injector
import com.xmartlabs.bigbang.core.controller.SharedPreferencesController
import com.xmartlabs.bigbang.core.helper.ObjectHelper
import com.xmartlabs.bigbang.ui.mvp.BaseMvpPresenter
import com.xmartlabs.template.controller.AuthController

import javax.inject.Inject

import lombok.Builder

class LoginPresenter @Inject constructor() : BaseMvpPresenter<LoginView>() {
  //TODO: Do something on login button clicked
  fun loginButtonClicked() = view?.gotoRecyclerExampleActivity()

  private fun setIsLoading(loading: Boolean) = view?.setIsLoading(loading)
}
