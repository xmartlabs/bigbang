package com.xmartlabs.bigbang.core.helper;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.core.exception.EntityNotFoundException;
import com.xmartlabs.bigbang.core.helper.function.Consumer;
import com.xmartlabs.bigbang.core.model.BuildInfo;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

import javax.inject.Inject;

import io.reactivex.exceptions.CompositeException;
import lombok.Getter;
import timber.log.Timber;

/** Handles any {@link Throwable} thrown. */
@SuppressWarnings("unused")
public final class GeneralErrorHelper {
  private final List<Class<?>> UNTRACKED_CLASSES = Arrays.asList(
      CancellationException.class,
      ConnectException.class,
      EntityNotFoundException.class,
      UnknownHostException.class
  );
  private final StackTraceElement DUMMY_STACK_TRACE_ELEMENT = new StackTraceElement("", "", null, -1);
  private final StackTraceElement[] DUMMY_STACK_TRACE_ELEMENT_ARRAY =
      new StackTraceElement[] {DUMMY_STACK_TRACE_ELEMENT};

  private final Map<Class<? extends Throwable>, Consumer<? super Throwable>> throwableHandlers = new HashMap<>();

  private final BuildInfo buildInfo;

  @Inject
  public GeneralErrorHelper(@NonNull BuildInfo buildInfo) {
    this.buildInfo = buildInfo;
  }

  @Getter
  private final io.reactivex.functions.Consumer<? super Throwable> generalErrorAction = t -> {
    if (t instanceof CompositeException) {
      CompositeException compositeException = (CompositeException) t;
      Stream.of(compositeException.getExceptions())
          .forEach(this::handleException);
    } else {
      handleException(t);
    }
    markExceptionAsHandled(t);
  };

  private void handleException(Throwable throwable) {
    if (!shouldHandleThrowable(throwable)) {
      if (buildInfo.isDebug()) {
        Timber.e(throwable, "Untracked exception");
      } else {
        Timber.d(throwable, "Untracked exception");
      }
      return;
    }

    if (throwableHandlers.containsKey(throwable.getClass())) {
      if (exceptionIsAlreadyBeingHandled(throwable)) {
        return;
      }
      throwableHandlers.get(throwable.getClass()).accept(throwable);
    } else {
      Timber.e(throwable);
    }
  }

  private boolean shouldHandleThrowable(Throwable throwable) {
    return !exceptionIsAlreadyBeingHandled(throwable)
        || !UNTRACKED_CLASSES.contains(throwable.getClass())
        || throwableHandlers.containsKey(throwable.getClass());
  }

  private boolean exceptionIsAlreadyBeingHandled(Throwable t) {
    return Objects.equals(t.getStackTrace(), DUMMY_STACK_TRACE_ELEMENT_ARRAY);
  }

  private void markExceptionAsHandled(Throwable t) {
    t.setStackTrace(DUMMY_STACK_TRACE_ELEMENT_ARRAY);
  }

  /**
   * Add a new handler for a specific {@link Throwable} class.
   *
   * @param clazz the {@link Throwable} class to be handled
   * @param handler the handler for the specified {@link Throwable}
   */
  public void setErrorHandlerForThrowable(Class<? extends Throwable> clazz, Consumer<? super Throwable> handler) {
    throwableHandlers.put(clazz, handler);
  }
}
