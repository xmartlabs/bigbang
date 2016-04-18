package com.xmartlabs.template.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.annimon.stream.Objects;
import com.crashlytics.android.Crashlytics;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.common.LoggedException;
import com.xmartlabs.template.controller.SessionController;

import java.io.IOException;

import javax.inject.Inject;

import lombok.Getter;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;
import rx.plugins.RxJavaErrorHandler;
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

  @Getter
  private static final RxJavaErrorHandler rxErrorHandler = new RxJavaErrorHandler() {
    @Override
    public void handleError(Throwable error) {
      super.handleError(error);
      generalErrorAction.call(error);
    }
  };

  public GeneralErrorHelper() {
    BaseProjectApplication.getContext().inject(this);
  }

  private static void logCrashlyticsError(Response<?> response) {
    String url = response.raw().request().url().toString();
    int resultCode = response.code();
    String headers = response.headers().toString();
    String body = null;
    Crashlytics.setString(CRASHLYTICS_KEY_URL, url);
    Crashlytics.setInt(CRASHLYTICS_KEY_STATUS_CODE, resultCode);
    Crashlytics.setString(CRASHLYTICS_KEY_RESPONSE_HEADERS, headers);
    try {
      body = response.errorBody().string();
    } catch (IOException e) {
      Timber.w(e, "Couldn't read error body");
    }
    Crashlytics.setString(CRASHLYTICS_KEY_RESPONSE_BODY, body);
    Timber.d("Set Crashlytics keys - result code = %d, headers = %s, url = %s, body = %s",
        resultCode,
        headers,
        url,
        body
    );
  }

  private static void clearCrashlyticsKeys() {
    Crashlytics.setString(CRASHLYTICS_KEY_URL, null);
    Crashlytics.setInt(CRASHLYTICS_KEY_STATUS_CODE, -1);
    Crashlytics.setString(CRASHLYTICS_KEY_RESPONSE_HEADERS, null);
    Crashlytics.setString(CRASHLYTICS_KEY_RESPONSE_BODY, null);
  }

  @Getter
  final static Action1<Throwable> generalErrorAction = t -> {
    if (t instanceof HttpException) {
      HttpException httpException = (HttpException) t;
      Response<?> response = httpException.response();
      if (t.getCause() instanceof LoggedException) {
        return;
      }

      if (Objects.equals(response.code(), 401)) {
        logOut();
      } else {
        logCrashlyticsError(response);
        Timber.e(t, null);
        clearCrashlyticsKeys();
        httpException.initCause(new LoggedException());
      }
    } else {
      Timber.e(t, null);
    }
  };

  private static void logOut() {
    finishLogOut();
    // TODO: take the user to an activity
  }

  /**
   * Triggers the actions that need to be done after logging out.
   *
   * If this is not done after log out, the log out sessionInterceptor could not catch the session token.
   */
  public static void finishLogOut() {
    dismissNotifications();
    // TODO: remove data from database too
  }

  public static void dismissNotifications() {
    // TODO
  }
}
