package com.xmartlabs.template.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/** Interface that defines a type that can log any type of {@link Throwable} exception. */
public interface Logger {
  /**
   * Logs a {@link Throwable} exception or an event that, while not an exception, is a deviation from the normal
   * course of events.
   *
   * @param logInfo the information associated with the event/exception
   * @param t the exception, if present
   */
  void log(@NonNull LogInfo logInfo, @Nullable Throwable t);
}
