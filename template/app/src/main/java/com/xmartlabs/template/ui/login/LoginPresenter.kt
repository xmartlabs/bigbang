package com.xmartlabs.template.ui.login

import com.annimon.stream.Optional
import com.xmartlabs.bigbang.core.Injector
import com.xmartlabs.bigbang.core.controller.SharedPreferencesController
import com.xmartlabs.bigbang.core.helper.ObjectHelper
import com.xmartlabs.bigbang.ui.mvp.BaseMvpPresenter
import com.xmartlabs.template.controller.AuthController

import javax.inject.Inject

import lombok.Builder

@Builder
class LoginPresenter : BaseMvpPresenter<LoginView>() {
  @Inject
  internal lateinit var authController: AuthController
  @Inject
  internal lateinit var objectHelper: ObjectHelper
  @Inject
  internal lateinit var sharedPreferencesController: SharedPreferencesController

  override fun attachView(view: LoginView) {
    super.attachView(view)
    Injector.inject(this)
  }

  fun loginButtonClicked() {
    //TODO: Do something on login button clicked
    Optional.ofNullable<LoginView>(view)
        .ifPresent { it.gotoRecyclerExampleActivity() }
  }

  private fun setIsLoading(loading: Boolean) {
    Optional.ofNullable<LoginView>(view)
        .ifPresent { view -> view.setIsLoading(loading) }
  }
}
