package com.xmartlabs.template.controller

import android.support.annotation.CheckResult

import com.xmartlabs.bigbang.core.controller.Controller
import com.xmartlabs.bigbang.core.providers.AccessTokenProvider
import com.xmartlabs.template.model.AuthResponse
import com.xmartlabs.template.model.Session
import com.xmartlabs.template.service.AuthService

import javax.inject.Inject

import io.reactivex.Single

class AuthController : Controller() {
  @Inject
  internal lateinit var accessTokenProvider: AccessTokenProvider
  @Inject
  internal lateinit var authService: AuthService
  @Inject
  internal lateinit var sessionController: SessionController

  //TODO: Change signature and method to match authService request to fetch the Access Token
  val accessToken: Single<Session>
    @CheckResult
    get() = authService.accessToken
        .compose(applySingleIoSchedulers<AuthResponse>())
        .filter { authResponse -> authResponse.accessToken != null }
        .toSingle()
        .flatMap { authResponse ->
          sessionController.setSession(Session())
              .doOnSuccess { accessTokenProvider.updateEntity(authResponse.accessToken) }
        }
}
