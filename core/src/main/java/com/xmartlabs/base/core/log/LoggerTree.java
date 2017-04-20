package com.xmartlabs.base.core.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Subclass of {@link Timber.DebugTree} that allows the captured exception/events to be logged with any user-defined
 * Logger, such as Crashlytics, Sentry, etc.
 */
public class LoggerTree extends Timber.DebugTree {
  /** {@link Logger} instances that can log any captured exception/event. **/
  private final List<Logger> loggers = new ArrayList<>();
  /** {@link Log} priorities that will be excluding from logging */
  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
  private final List<Integer> excludedPriorities = new ArrayList<>();

  @Inject
  public LoggerTree() {
  }

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

  /**
   * Adds a new {@link Logger}.
   *
   * @param logger {@link Logger} to be added
   */
  public void addLogger(@NonNull Logger logger) {
    loggers.add(logger);
  }

  /**
   * Removes a {@link Logger}, so no new events will be processed by it.
   *
   * @param logger the {@link Logger} to be removed
   */
  public void removeLogger(@NonNull Logger logger) {
    loggers.remove(logger);
  }

  /**
   * Adds a priority to be excluded from logging.
   *
   * @param priority the priority to be excluded
   */
  public void excludeLogPriority(int priority) {
    excludedPriorities.add(priority);
  }

  /**
   * Removes the {@code priority} from the exclusion list. Thus, all the new events with the priority will be logged.
   *
   * @param priority the priority whose events are to be logged
   */
  public void includeLogPriority(int priority) {
    excludedPriorities.remove(priority);
  }
}
