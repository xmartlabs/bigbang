package com.scottruth.timeoffandroid.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.annimon.stream.Objects;
import com.crashlytics.android.Crashlytics;
import com.scottruth.timeoffandroid.TimeOffApplication;
import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.service.AuthService;

import java.io.IOException;

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
    public static final String CRASHLYTICS_KEY_URL = "url";

    @Inject
    Context applicationContext;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SessionController sessionController;

    public GeneralErrorHelper() {
        TimeOffApplication.getContext().inject(this);
    }

    private final Action1<Throwable> generalErrorAction = t -> {
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            Response<?> response = httpException.response();

            if (Objects.equals(response.code(), AuthService.HTTP_UNAUTHORIZED)) {
                logOut();
            } else {
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
    };

    private void logOut() {
        finishLogOut();
        // TODO: take the user to an activity
    }

    public Action1<Throwable> getGeneralErrorAction() {
        return generalErrorAction;
    }

    // If this is not done after log out, the log out sessionInterceptor could not catch the session token.
    public void finishLogOut() {
        dismissNotifications();
        // TODO: remove data from database too
    }

    public void dismissNotifications() {
        // TODO
    }
}
