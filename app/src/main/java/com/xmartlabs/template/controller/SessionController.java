package com.xmartlabs.template.controller;

import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

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
  SharedPreferencesController sharedPreferencesController;

  /**
   * Retrieves the session information from the authResponse and stores it.
   *
   * @param authResponse the Authentication response from the service from which the session information can be obtained
   * @return {@code Session} instance
   */
  public Session setSession(@NonNull AuthResponse authResponse) {
    Session session = getSessionFromAuthInformation(authResponse);
    setSession(session);
    return session;
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
    return sharedPreferencesController.getEntity(PREFERENCES_KEY_SESSION, Session.class);
  }

  /**
   * Stores the {@code session} into the {@link SharedPreferences}.
   *
   * @param session the {@link Session} object to be stored
   */
  public void setSession(@NonNull Session session) {
    sharedPreferencesController.saveEntity(PREFERENCES_KEY_SESSION, session);
  }

  /** Deletes the session information */
  public void deleteSession() {
    sharedPreferencesController.deleteEntity(PREFERENCES_KEY_SESSION);
  }

  /**
   * Returns whether the {@link Session} information is present on the device.
   * @return whether or not the {@link Session} information exists
   */
  @CheckResult
  @SuppressWarnings("unused")
  public boolean isSessionAlive() {
    return sharedPreferencesController.hasEntity(PREFERENCES_KEY_SESSION);
  }
}
