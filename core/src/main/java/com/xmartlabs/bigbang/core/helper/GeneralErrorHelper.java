package com.xmartlabs.bigbang.core.helper;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.core.exception.EntityNotFoundException;
import com.xmartlabs.bigbang.core.helper.function.Consumer;

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
  private final StackTraceElement DUMMY_STACK_TRACE_ELEMENT =
      new StackTraceElement("General error logger", "", null, -1);
  private final Map<Class<? extends Throwable>, Consumer<? super Throwable>> throwableHandlers = new HashMap<>();

  @Inject
  public GeneralErrorHelper() {}

  @Getter
  private final Consumer<? super Throwable> generalErrorAction = t -> {
    if (t instanceof CompositeException) {
      CompositeException compositeException = (CompositeException) t;
      Stream.of(compositeException.getExceptions())
          .forEach(this::handleException);
    } else {
      handleException(t);
    }
  };

  private void handleException(@NonNull Throwable throwable) {
    if (!shouldHandleThrowable(throwable)
        || (throwable.getCause() != null && !shouldHandleThrowable(throwable.getCause()))) {
      if (!exceptionIsAlreadyBeingHandled(throwable)) {
        Timber.d(throwable, "Untracked exception");
      }
      markExceptionAsHandled(throwable);
      return;
    }

    if (throwableHandlers.containsKey(throwable.getClass())) {
      throwableHandlers.get(throwable.getClass()).accept(throwable);
    } else {
      Timber.e(throwable);
    }
    markExceptionAsHandled(throwable);
  }

  private boolean shouldHandleThrowable(@NonNull Throwable throwable) {
    return !exceptionIsAlreadyBeingHandled(throwable)
        && !UNTRACKED_CLASSES.contains(throwable.getClass());
  }

  private boolean exceptionIsAlreadyBeingHandled(@NonNull Throwable throwable) {
    return Stream.ofNullable(Arrays.asList(throwable.getStackTrace()))
        .anyMatch(value -> Objects.equals(value, DUMMY_STACK_TRACE_ELEMENT));
  }

  private void markExceptionAsHandled(Throwable throwable) {
    StackTraceElement[] stacktrace = Stream
        .concat(
            Stream.of(DUMMY_STACK_TRACE_ELEMENT),
            Stream.of(throwable.getStackTrace()))
        .toArray(StackTraceElement[]::new);
    throwable.setStackTrace(stacktrace);
  }

  /**
   * Add a new handler for a specific {@link Throwable} class.
   *
   * @param clazz   the {@link Throwable} class to be handled
   * @param handler the handler for the specified {@link Throwable}
   */
  public void setErrorHandlerForThrowable(Class<? extends Throwable> clazz, Consumer<? super Throwable> handler) {
    throwableHandlers.put(clazz, handler);
  }
}
