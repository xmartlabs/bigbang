package com.xmartlabs.bigbang.core.controller;

import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.xmartlabs.bigbang.core.model.SessionType;

import javax.inject.Inject;

import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

/**
 * Controller that manages the Session of the Application.
 *
 * The Session will be stored via the {@link SharedPreferencesController}.
 */
@RequiredArgsConstructor
public class CoreSessionController extends Controller {
  private static final String PREFERENCES_KEY_SESSION = "session";

  private final Class<? extends SessionType> concreteSessionType;

  @Inject
  SharedPreferencesController sharedPreferencesController;

  /**
   * Retrieves the current stored {@link SessionType}, if it exists.
   *
   * @return the current {@link SessionType}, or {@code null} if none exists
   */
  @CheckResult
  @NonNull
  public Optional<? extends SessionType> getSession() {
    return sharedPreferencesController
        .getEntity(PREFERENCES_KEY_SESSION, concreteSessionType);
  }

  /**
   * Stores the {@code session} into the {@link SharedPreferences}.
   *
   * @param session the {@code S} object to be stored
   * @param <S> the {@link SessionType} object to be stored
   *
   * @return {@code Single<S>} object. Upon subscription, it will only fail if the session could not be stored
   */
  @CheckResult
  @NonNull
  public <S extends SessionType> Single<S> setSession(@NonNull S session) {
    return sharedPreferencesController.saveEntity(PREFERENCES_KEY_SESSION, session);
  }

  /** Deletes the session information */
  public void deleteSession() {
    sharedPreferencesController.deleteEntity(PREFERENCES_KEY_SESSION);
  }

  /**
   * Returns whether the {@link SessionType} information is present on the device.
   *
   * @return whether or not the {@link SessionType} information exists
   */
  @CheckResult
  @SuppressWarnings("unused")
  public boolean isSessionAlive() {
    return sharedPreferencesController.hasEntity(PREFERENCES_KEY_SESSION);
  }
}
