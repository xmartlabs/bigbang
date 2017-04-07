package com.xmartlabs.template.providers;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.xmartlabs.template.common.EntityProvider;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

/** Provides the access token in order to be added in the service requests */
public class AccessTokenProvider implements EntityProvider<String> {
  private static final String SESSION_HEADER = "session";

  @NonNull
  private final SessionController sessionController;

  @Inject
  public AccessTokenProvider(@NonNull SessionController sessionController) {
    this.sessionController = sessionController;
  }

  @Override
  public Optional<String> provideEntity() {
    return sessionController.getSession()
        .map(Session::getAccessToken)
        .flatMap(Optional::ofNullable);
  }

  @NonNull
  public String provideAccessTokenHeaderKey() {
    return SESSION_HEADER;
  }

  @Override
  public void updateEntity(String accessToken) {
    sessionController.getSession()
        .executeIfPresent(session -> session.setAccessToken(accessToken))
        .ifPresent(sessionController::setSession);
  }

  @Override
  public void deleteEntity() {
    sessionController.getSession()
        .executeIfPresent(session -> session.setAccessToken(null))
        .ifPresent(sessionController::setSession);
  }
}
