package com.scottruth.timeoffandroid.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.scottruth.timeoffandroid.TimeOffApplication;
import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.model.Session;
import com.scottruth.timeoffandroid.service.AuthService;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import javax.inject.Inject;

import retrofit.HttpException;
import retrofit.Response;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by santiago on 12/10/15.
 */
public class GeneralErrorHelper {
    private static final String CRASHLYTICS_KEY_RESPONSE_BODY = "response_body";
    private static final String CRASHLYTICS_KEY_RESPONSE_HEADERS = "response_headers";
    private static final String CRASHLYTICS_KEY_STATUS_CODE = "status_code";
    private static final String CRASHLYTICS_KEY_URL = "url";

    @Inject
    Context applicationContext;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SessionController sessionController;

    private boolean tracked = false;
    private boolean identified = false;

    public GeneralErrorHelper() {
        TimeOffApplication.getContext().inject(this);
    }

    private final Action1<Throwable> generalErrorAction = t -> {
        if (! (t instanceof CancellationException)) {
            if (t instanceof HttpException) {
                HttpException httpException = (HttpException) t;
                Response<?> response = httpException.response();

                if (response.code() == AuthService.HTTP_UNAUTHORIZED) {
                    logOut();
                } else if (response.code() == AuthService.HTTP_CODE_USER_NOT_FOUND) {
                    Session session = sessionController.getSession();
                    if (session != null) {
                        logOut();
                    }
                } else if (response.code() != AuthService.HTTP_CODE_USER_ALREADY_EXISTS) {
                    Crashlytics.setString(CRASHLYTICS_KEY_URL, response.raw().request().urlString());
                    Crashlytics.setInt(CRASHLYTICS_KEY_STATUS_CODE, response.code());
                    Crashlytics.setString(CRASHLYTICS_KEY_RESPONSE_HEADERS, response.headers().toString());

                    try {
                        Crashlytics.setString(CRASHLYTICS_KEY_RESPONSE_BODY, response.errorBody().string());
                    } catch (IOException e) {
                        Timber.e(e, "Couldn't read error body");
                    }

                    Timber.e(t, null);
                }
            } else {
                Timber.e(t, null);
            }
        }
    };

    private void logOut() {
        finishLogOut();

//        Intent intent = Henson.with(applicationContext).gotoWelcomeActivity().build();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        applicationContext.startActivity(intent);
    }

    public Action1<Throwable> getGeneralErrorAction() {
        return generalErrorAction;
    }

    // If this is not done after log out, the log out sessionInterceptor could not catch the session token.
    public void finishLogOut() {
        dismissNotifications();
//        databaseHelper.deleteAll();
//        sessionController.deleteSession();
//        LoginManager.getInstance().logOut();
//        if (layerClient.isAuthenticated()) {
//            layerClient.deauthenticate();
//        }
//        Analytics.with(applicationContext).reset();
//        setTracked(false);
//        setIdentified(false);
//        Smooch.logout();
//        Once.clearDone(LookaroundController.ONCE_TAG_LOOKAROUND);
    }

    public void dismissNotifications() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        for (String key : LayerPushReceiver.PREFERENCES_KEYS) {
//            editor.remove(key);
//        }
//        editor.apply();
//
//        NotificationManager notificationManager = (NotificationManager) ThreenderApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        for (int notificationId : LayerPushReceiver.PUSH_NOTIFICATIONS_IDS) {
//            notificationManager.cancel(notificationId);
//        }
    }

    public boolean isTracked() {
        return tracked;
    }

    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }

    public boolean isIdentified() {
        return identified;
    }

    public void setIdentified(boolean identified) {
        this.identified = identified;
    }
}
