package com.xmartlabs.template.log;

import com.annimon.stream.Stream;

import java.util.List;

import lombok.Builder;
import lombok.Singular;
import timber.log.Timber;

@Builder
public class LoggerTree extends Timber.DebugTree {
  @Singular
  private final List<Logger> loggers;
  private final List<Integer> excludedPriorities;

  @Override
  protected void log(int priority, String tag, String message, Throwable t) {
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
}
