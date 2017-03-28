package com.xmartlabs.template.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.crashlytics.android.Crashlytics;
import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.BuildConfig;
import com.xmartlabs.template.common.exeption.EntityNotFoundException;
import com.xmartlabs.template.common.exeption.ServiceExceptionWithMessage;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.log.LoggerTree;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CancellationException;

import javax.inject.Inject;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Consumer;
import lombok.Getter;
import retrofit2.HttpException;
import timber.log.Timber;

@SuppressWarnings("unused")
public final class GeneralErrorHelper {
  private static final String CRASHLYTICS_KEY_RESPONSE_BODY = "response_body";
  private static final String CRASHLYTICS_KEY_RESPONSE_HEADERS = "response_headers";
  private static final String CRASHLYTICS_KEY_STATUS_CODE = "status_code";
  public static final String CRASHLYTICS_KEY_URL = "url";

  private final List<Class<?>> UNTRACKED_CLASSES = Arrays.asList(
      CancellationException.class,
      ConnectException.class,
      EntityNotFoundException.class,
      UnknownHostException.class
  );
  private final StackTraceElement DUMMY_STACK_TRACE_ELEMENT = new StackTraceElement("", "", null, -1);
  private final StackTraceElement[] DUMMY_STACK_TRACE_ELEMENT_ARRAY =
      new StackTraceElement[] {DUMMY_STACK_TRACE_ELEMENT};

  @Inject
  Context applicationContext;
  @Inject
  LoggerTree loggerTree;
  @Inject
  SessionController sessionController;
  @Inject
  SharedPreferences sharedPreferences;

  @Getter
  final Consumer<? super Throwable> generalErrorAction = t -> {
    if (t instanceof CompositeException) {
      CompositeException compositeException = (CompositeException) t;
      Stream.of(compositeException.getExceptions())
          .forEach(this::handleException);
    } else {
      handleException(t);
    }
    markExceptionAsHandled(t);
  };

  public GeneralErrorHelper() {
    BaseProjectApplication.getContext().inject(this);
  }

  private void logError(ServiceExceptionWithMessage exceptionWithMessage) {
    Map<String, String> information = new HashMap<>();
    information.put(CRASHLYTICS_KEY_URL, ServiceHelper.getUrl(exceptionWithMessage.getResponse().raw()));
    information.put(CRASHLYTICS_KEY_STATUS_CODE, String.valueOf(exceptionWithMessage.getCode()));
    information.put(CRASHLYTICS_KEY_RESPONSE_HEADERS, exceptionWithMessage.getResponse().headers().toString());
    information.put(CRASHLYTICS_KEY_RESPONSE_BODY, exceptionWithMessage.getErrorBody());

    loggerTree.log(information);
  }

  private void handleException(Throwable throwable) {
    if (!shouldHandleThrowable(throwable)) {
      if (BuildConfig.DEBUG) {
        Timber.e(throwable, "Untracked exception");
      } else {
        Timber.d(throwable, "Untracked exception");
      }
      return;
    }

    if (throwable instanceof HttpException || throwable instanceof ServiceExceptionWithMessage) {
      if (exceptionIsAlreadyBeingHandled(throwable)) {
        return;
      }
      ServiceExceptionWithMessage exceptionWithMessage = throwable instanceof ServiceExceptionWithMessage
          ? (ServiceExceptionWithMessage) throwable
          : new ServiceExceptionWithMessage((HttpException) throwable);
      logError(exceptionWithMessage);
    } else {
      Timber.e(throwable);
    }
  }

  private boolean shouldHandleThrowable(Throwable throwable) {
    return !(exceptionIsAlreadyBeingHandled(throwable)
        || UNTRACKED_CLASSES.contains(throwable.getClass())
        || (throwable instanceof ServiceExceptionWithMessage
        && !shouldHandleServiceExceptionWithMessage((ServiceExceptionWithMessage) throwable)));
  }

  private boolean shouldHandleServiceExceptionWithMessage(ServiceExceptionWithMessage exception) {
    return exception.getErrorListSize() <= 0;
  }

  private boolean exceptionIsAlreadyBeingHandled(Throwable t) {
    return Objects.equals(t.getStackTrace(), DUMMY_STACK_TRACE_ELEMENT_ARRAY);
  }

  private void markExceptionAsHandled(Throwable t) {
    t.setStackTrace(DUMMY_STACK_TRACE_ELEMENT_ARRAY);
  }
}
