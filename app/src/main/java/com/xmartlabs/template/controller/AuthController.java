package com.xmartlabs.template.controller;

import android.support.annotation.NonNull;

import com.xmartlabs.template.common.RetryWithDelay;
import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.LoginRequest;
import com.xmartlabs.template.model.Session;
import com.xmartlabs.template.service.AuthService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import lombok.val;
import rx.Completable;
import rx.Single;
import rx.Subscription;

/**
 * Created by santiago on 01/03/16.
 */
public class AuthController extends ServiceController {
  @Inject
  AuthService authService;
  @Inject
  SessionController sessionController;

  public Single<AuthResponse> login(LoginRequest loginRequest) {
    // TODO
    //authService.login();
    return Single.just(new AuthResponse())
        .doOnSuccess(authResponse -> {
          Session session = sessionController.setSession(authResponse);
          setLoginInfo(session);
        });
  }

  public void setLoginInfo(@NonNull Session session) {
    // TODO
  }

  public void registerGcmToken(String gcmToken) {
    val retryWithDelay = new RetryWithDelay(1, TimeUnit.MINUTES) {
      @Override
      protected boolean shouldRetry(Throwable throwable) {
        return throwable instanceof IOException;
      }
    };
    Completable.create(Completable.CompletableSubscriber::onCompleted) // TODO mirland: 4/18/16 add register gcm token service
        .retryWhen(retryWithDelay)
        .subscribe(new Completable.CompletableSubscriber() {
          @Override
          public void onCompleted() {
            Session session = sessionController.getSession();
            if (session == null) {
              session = new Session();
            }
            session.setGcmToken(gcmToken);
            sessionController.setSession(session);
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onSubscribe(Subscription d) {

          }
        });
  }
}
