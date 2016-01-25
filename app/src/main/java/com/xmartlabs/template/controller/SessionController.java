package com.xmartlabs.template.controller;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonParseException;
import com.xmartlabs.template.helper.GsonHelper;
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
    SharedPreferences sharedPreferences;

    public void setSession(@NonNull Session session) {
        String sessionJsonString = GsonHelper.getGson().toJson(session);
        sharedPreferences
                .edit()
                .putString(PREFERENCES_KEY_SESSION, sessionJsonString)
                .apply();
    }

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
            return GsonHelper.getGson().fromJson(sessionJsonString, Session.class);
        } catch (JsonParseException e) {
            Timber.w(e, "Error while deserializing the stored session");
            deleteSession();
            return null;
        }
    }

    public void deleteSession() {
        sharedPreferences
                .edit()
                .remove(PREFERENCES_KEY_SESSION)
                .apply();
    }

    public boolean isSessionAlive() {
        return sharedPreferences
                .contains(PREFERENCES_KEY_SESSION);
    }
}
