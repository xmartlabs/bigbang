package com.xmartlabs.template.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Singular;
import timber.log.Timber;

/**
 * Subclass of {@link Timber.DebugTree} that allows the captured exception/events to be logged with any user-defined
 * Logger, such as Crashlytics, Sentry, etc.
 */
@Builder
public class LoggerTree extends Timber.DebugTree {
  /** {@link Logger} instances that can log any captured exception/event. **/
  @Singular
  private final List<Logger> loggers;
  /** {@link Log} priorities that will be excluding from logging */
  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
  private final List<Integer> excludedPriorities;

  @Override
  protected void log(int priority, @Nullable String tag, @Nullable String message,
                     @Nullable Throwable t) {
    if (excludedPriorities.contains(priority)) {
      return;
    }

    LogInfo logInfo = LogInfo.builder()
        .priority(priority)
        .tag(tag)
        .message(message)
        .build();
    Stream.ofNullable(loggers)
        .forEach(logger -> logger.log(logInfo, t));
  }

  /**
   * Logs a set of information related to an event that deviates from the normal course of events.
   *
   * @param logInformation the information to be logged
   */
  public void log(@NonNull Map<String, String> logInformation, @Nullable Throwable t) {
    Stream.ofNullable(loggers)
        .forEach(logger -> logger.log(logInformation, t));
  }
}
