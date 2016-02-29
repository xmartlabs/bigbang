package com.xmartlabs.template.controller;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

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

  @SuppressWarnings("unused")
  public Session setSession(@NonNull AuthResponse authResponse) {
    Session session = getSession();
    if (session == null) {
      session = new Session();
    }
    session.updateSession(authResponse);
    setSession(session);
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

  public void setSession(@NonNull Session session) {
    String sessionJsonString = gson.toJson(session);
    sharedPreferences
        .edit()
        .putString(PREFERENCES_KEY_SESSION, sessionJsonString)
        .apply();
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
