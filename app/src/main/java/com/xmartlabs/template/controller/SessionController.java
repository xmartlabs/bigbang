package com.xmartlabs.template.controller;

import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Exceptional;
import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

/**
 * Controller that manages the Session of the Application.
 *
 * The Session will be stored in the {@link SharedPreferences} as a String.
 * Thus, the {@link Session} object is serialized using {@link Gson}.
 * The session is retrieved once from disk and then kept in memory for faster access.
 */
public class SessionController extends Controller {
  private static final String PREFERENCES_KEY_SESSION = "session";

  @Inject
  Gson gson;
  @Inject
  SharedPreferences sharedPreferences;

  private Optional<Session> session = Optional.empty();

  /**
   * Retrieves the session information from the authResponse and stores it.
   *
   * @param authResponse the Authentication response from the service from which the session information can be obtained
   * @return {@code Single<Session>} instance, which will can only fail on saving the information to the {@link SharedPreferences}
   */
  @CheckResult
  @NonNull
  public Single<Session> setSession(@NonNull AuthResponse authResponse) {
    final Session session = getSessionFromAuthInformation(authResponse);
    return setSession(session);
  }

  /**
   * Retrieves the stored session, if exists, or creates a new {@link Session} object.
   * Then, the {@link Session} object is updated with the information from the Authentication response object.
   *
   * @param authResponse the Authentication response from the service from which the session information will be updated
   * @return the {@link Session} object just created or updated
   */
  @CheckResult
  @NonNull
  private Session getSessionFromAuthInformation(@NonNull AuthResponse authResponse) {
    Session session = getSession()
        .orElse(new Session());
    session.updateSession(authResponse);
    return session;
  }

  /**
   * Retrieves the current stored {@link Session}, if it exists.
   *
   * Only upon first request the {@link Session} object will be queried from {@link SharedPreferences}.
   * Then, it will be stored in memory for faster access.
   *
   * @return the current {@link Session}, or {@code null} if none exists
   */
  @CheckResult
  @NonNull
  public Optional<Session> getSession() {
    return Optional.of(session).orElseGet(() -> {
      String sessionJsonString = sharedPreferences.getString(PREFERENCES_KEY_SESSION, null);

      return Exceptional.of(() -> gson.fromJson(sessionJsonString, Session.class))
          .ifException(e -> {
            Timber.w(e, "Error while deserializing the stored session");
            deleteSession();
          })
          .getOptional();
    });
  }

  /**
   * Stores the {@code session} into the {@link SharedPreferences}.
   *
   * @param session the {@link Session} object to be stored
   * @return {@code Single<Session>} object. Upon subscription, it will only fail if the session could not be stored
   */
  @CheckResult
  @NonNull
  public Single<Session> setSession(@NonNull Session session) {
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
   * Returns whether the {@link Session} information is present on the device.
   * @return whether or not the {@link Session} information exists
   */
  @CheckResult
  @SuppressWarnings("unused")
  public boolean isSessionAlive() {
    return sharedPreferences
        .contains(PREFERENCES_KEY_SESSION);
  }
}
