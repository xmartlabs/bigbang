package com.xmartlabs.base.core.providers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Optional;
import com.xmartlabs.base.core.common.EntityProvider;
import com.xmartlabs.base.core.controller.SessionController;
import com.xmartlabs.base.core.model.SessionType;
import com.xmartlabs.base.core.module.SessionInterceptor;

import javax.inject.Inject;

/**
 * Provides the access token in order to be added in the service requests.
 * It's used by the {@link SessionInterceptor}.
 */
public class AccessTokenProvider implements EntityProvider<String> {
  private static final String AUTH_TOKEN_HEADER_KEY = "session";

  @NonNull
  private final SessionController<SessionType> sessionController;

  @Inject
  public AccessTokenProvider(@NonNull SessionController sessionController) {
    //noinspection unchecked
    this.sessionController = sessionController;
  }

  @Override
  public Optional<String> provideEntity() {
    return sessionController.getSession()
        .map(SessionType::getAccessToken)
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
  public void updateEntity(@Nullable String accessToken) {
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
