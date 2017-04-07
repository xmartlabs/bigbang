package com.xmartlabs.template.providers;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.xmartlabs.template.common.EntityProvider;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.model.Session;
import com.xmartlabs.template.module.SessionInterceptor;

import javax.inject.Inject;

/**
 * Provides the access token in order to be added in the service requests.
 * It's used by the {@link SessionInterceptor}.
 */
public class AccessTokenProvider implements EntityProvider<String> {
  private static final String AUTH_TOKEN_HEADER_KEY = "session";

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

  /**
   * Provides the access token header key.
   * This should be overridden to change the header key.
   *
   * @return the access token header key
   */
  @NonNull
  public String provideAccessTokenHeaderKey() {
    return AUTH_TOKEN_HEADER_KEY;
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
