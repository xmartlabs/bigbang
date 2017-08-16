package com.xmartlabs.template.controller

import android.support.annotation.CheckResult
import com.xmartlabs.bigbang.core.controller.Controller
import com.xmartlabs.bigbang.core.controller.SessionController
import com.xmartlabs.bigbang.core.providers.AccessTokenProvider
import com.xmartlabs.template.model.Session
import com.xmartlabs.template.service.AuthService
import io.reactivex.Single
import javax.inject.Inject

class AuthController : Controller() {
  @Inject
  internal lateinit var accessTokenProvider: AccessTokenProvider
  @Inject
  internal lateinit var authService: AuthService
  @Inject
  internal lateinit var sessionController: SessionController

  //TODO: Change signature and method to match authService request to fetch the Access Token
  val accessToken: Single<out Session>
    @CheckResult
    get() = authService.accessToken
        .applyIoSchedulers()
        .filter { authResponse -> authResponse.accessToken != null }
        .toSingle()
        .map { Session(it.accessToken) }
        .doOnSuccess { sessionController.session = it }
}
