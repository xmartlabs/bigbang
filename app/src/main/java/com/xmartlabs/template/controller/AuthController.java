package com.xmartlabs.template.controller;

import android.support.annotation.NonNull;

import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.LoginRequest;
import com.xmartlabs.template.model.Session;
import com.xmartlabs.template.service.AuthService;

import javax.inject.Inject;

import rx.Single;

/**
 * Created by santiago on 01/03/16.
 */
public class AuthController extends ServiceController {
  @Inject
  AuthService authService;
  @Inject
  SessionController sessionController;

  public Single<Session> login(LoginRequest loginRequest) {
    // TODO
    //authService.login();
    return Single.just(new AuthResponse())
            .flatMap(sessionController::setSession)
            .doOnSuccess(this::setLoginInfo);
  }

  public void setLoginInfo(@NonNull Session session) {
    // TODO
  }
}
