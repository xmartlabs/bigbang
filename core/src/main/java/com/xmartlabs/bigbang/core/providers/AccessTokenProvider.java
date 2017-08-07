package com.xmartlabs.bigbang.core.providers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Optional;
import com.xmartlabs.bigbang.core.Injector;
import com.xmartlabs.bigbang.core.common.EntityProvider;
import com.xmartlabs.bigbang.core.controller.CoreSessionController;
import com.xmartlabs.bigbang.core.model.SessionType;
import com.xmartlabs.bigbang.core.module.SessionInterceptor;

import javax.inject.Inject;

/**
 * Provides the access token in order to be added in the service requests.
 * It's used by the {@link SessionInterceptor}.
 */
public class AccessTokenProvider implements EntityProvider<String> {
  private static final String AUTH_TOKEN_HEADER_KEY = "session";

  @Inject
  CoreSessionController coreSessionController;

  public AccessTokenProvider() {
    Injector.inject(this);
  }

  @Override
  public Optional<String> provideEntity() {
    return coreSessionController.getSession()
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
    coreSessionController.getSession()
        .executeIfPresent(session -> session.setAccessToken(accessToken))
        .ifPresent(coreSessionController::setSession);
  }

  @Override
  public void deleteEntity() {
    coreSessionController.getSession()
        .executeIfPresent(session -> session.setAccessToken(null))
        .ifPresent(coreSessionController::setSession);
  }
}
