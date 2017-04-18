package com.xmartlabs.base.core.helper;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.base.core.exception.EntityNotFoundException;
import com.xmartlabs.base.core.exception.ServiceExceptionWithMessage;
import com.xmartlabs.base.core.log.LoggerTree;
import com.xmartlabs.base.core.model.BuildInfo;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

  private final LoggerTree loggerTree;
  private final BuildInfo buildInfo;

  @Inject
  public GeneralErrorHelper(@NonNull LoggerTree loggerTree, @NonNull BuildInfo buildInfo) {
    this.loggerTree = loggerTree;
    this.buildInfo = buildInfo;
  }

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

  private void logError(ServiceExceptionWithMessage exceptionWithMessage) {
    Map<String, String> information = new HashMap<>();
    information.put(CRASHLYTICS_KEY_URL, ServiceHelper.getUrl(exceptionWithMessage.getResponse().raw()));
    information.put(CRASHLYTICS_KEY_STATUS_CODE, String.valueOf(exceptionWithMessage.getCode()));
    information.put(CRASHLYTICS_KEY_RESPONSE_HEADERS, exceptionWithMessage.getResponse().headers().toString());
    information.put(CRASHLYTICS_KEY_RESPONSE_BODY, exceptionWithMessage.getErrorBody());

    loggerTree.log(information, exceptionWithMessage);
  }

  private void handleException(Throwable throwable) {
    if (!shouldHandleThrowable(throwable)) {
      if (buildInfo.isDebug()) {
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
