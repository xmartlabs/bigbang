package com.xmartlabs.template.controller;

import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

import rx.Completable;
import rx.Single;
import timber.log.Timber;

/**
 * Created by santiago on 30/09/15.
 */
public class SessionController extends Controller {
  private static final String PREFERENCES_KEY_SESSION = "session";

  @Inject
  Gson gson;
  @Inject
  SharedPreferences sharedPreferences;

  @CheckResult
  public Single<Session> setSession(@NonNull AuthResponse authResponse) {
    final Session session = getSessionFromAuthInformation(authResponse);
    return setSession(session)
            .toSingle(() -> session);
  }

  @NonNull
  private Session getSessionFromAuthInformation(@NonNull AuthResponse authResponse) {
    Session session = getSession();
    if (session == null) {
      session = new Session();
    }
    session.updateSession(authResponse);
    return session;
  }

  @Nullable
  public Session getSession() {
    String sessionJsonString = sharedPreferences
        .getString(PREFERENCES_KEY_SESSION, null);
    try {
      return gson.fromJson(sessionJsonString, Session.class);
    } catch (JsonParseException e) {
      Timber.w(e, "Error while deserializing the stored session");
      deleteSession();
      return null;
    }
  }

  @CheckResult
  public Completable setSession(@NonNull Session session) {
    return Completable.create(subscriber -> {
      String sessionJsonString = gson.toJson(session);
      boolean committed = sharedPreferences
              .edit()
              .putString(PREFERENCES_KEY_SESSION, sessionJsonString)
              .commit();
      if (committed) {
        subscriber.onCompleted();
      } else {
        subscriber.onError(new RuntimeException("The session change could not be committed"));
      }
    });
  }

  public void deleteSession() {
    sharedPreferences
        .edit()
        .remove(PREFERENCES_KEY_SESSION)
        .apply();
  }

  @SuppressWarnings("unused")
  public boolean isSessionAlive() {
    return sharedPreferences
        .contains(PREFERENCES_KEY_SESSION);
  }
}
