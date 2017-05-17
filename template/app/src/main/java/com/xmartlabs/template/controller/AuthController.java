package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.xmartlabs.bigbang.core.controller.Controller;
import com.xmartlabs.bigbang.core.providers.AccessTokenProvider;
import com.xmartlabs.template.model.Session;
import com.xmartlabs.template.service.AuthService;

import javax.inject.Inject;

import io.reactivex.Single;

public class AuthController extends Controller {
  @Inject
  AccessTokenProvider accessTokenProvider;
  @Inject
  AuthService authService;
  @Inject
  SessionController sessionController;

  //TODO: Change signature and method to match authService request to fetch the Access Token
  @CheckResult
  @NonNull
  public Single<Session> getAccessToken() {
    return authService.getAccessToken()
        .compose(applySingleIoSchedulers())
        .filter(authResponse -> authResponse.getAccessToken() != null)
        .toSingle()
        .flatMap(authResponse -> sessionController.setSession(new Session())
            .doOnSuccess(session -> accessTokenProvider.updateEntity(authResponse.getAccessToken())));
  }
}
