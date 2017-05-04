package com.xmartlabs.bigbang.core.controller;

import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.xmartlabs.bigbang.core.model.SessionType;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Controller that manages the Session of the Application.
 *
 * The Session will be stored in the {@link SharedPreferences} as a String.
 * Thus, the {@link SessionType} object is serialized using {@link Gson}.
 * The session is retrieved once from disk and then kept in memory for faster access.
 */
public abstract class SessionController extends Controller {
  private static final String PREFERENCES_KEY_SESSION = "session";

  @Inject
  Gson gson;
  @Inject
  SharedPreferences sharedPreferences;

  private Optional<SessionType> session = Optional.empty();

  /**
   * Retrieves the current stored {@link SessionType}, if it exists.
   *
   * Only upon first request the {@link SessionType} object will be queried from {@link SharedPreferences}.
   * Then, it will be stored in memory for faster access.
   *
   * @return the current {@link SessionType}, or {@code null} if none exists
   */
  @CheckResult
  @NonNull
  public Optional<SessionType> getAbstractSession() {
    return Optional.of(session).orElseGet(() -> {
      String sessionJsonString = sharedPreferences.getString(PREFERENCES_KEY_SESSION, null);
      return deserializeSession(sessionJsonString);
    });
  }

  protected abstract Optional<SessionType> deserializeSession(String json);

  /**
   * Stores the {@code session} into the {@link SharedPreferences}.
   *
   * @param session the {@link SessionType} object to be stored
   * @return {@code Single<Session>} object. Upon subscription, it will only fail if the session could not be stored
   */
  @CheckResult
  @NonNull
  public <S extends SessionType> Single<S> setSession(@NonNull S session) {
    return Single.fromCallable(() -> {
      String sessionJsonString = gson.toJson(session);
      boolean committed = sharedPreferences
          .edit()
          .putString(PREFERENCES_KEY_SESSION, sessionJsonString)
          .commit();

      if (committed) {
        this.session = Optional.of(session);
        return session;
      }
      throw new RuntimeException("The session change could not be committed");
    });
  }

  /** Deletes the session information */
  public void deleteSession() {
    session = Optional.empty();
    sharedPreferences
        .edit()
        .remove(PREFERENCES_KEY_SESSION)
        .apply();
  }

  /**
   * Returns whether the {@link SessionType} information is present on the device.
   * @return whether or not the {@link SessionType} information exists
   */
  @CheckResult
  @SuppressWarnings("unused")
  public boolean isSessionAlive() {
    return sharedPreferences
        .contains(PREFERENCES_KEY_SESSION);
  }
}
