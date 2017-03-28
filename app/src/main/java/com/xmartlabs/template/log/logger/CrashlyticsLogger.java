package com.xmartlabs.template.log.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.crashlytics.android.Crashlytics;
import com.xmartlabs.template.log.LogInfo;
import com.xmartlabs.template.log.Logger;

import java.util.Map;

/** {@link Logger} that logs exceptions/events to Crashlytics. **/
public class CrashlyticsLogger implements Logger {
  @Override
  public void log(@NonNull LogInfo logInfo, @Nullable Throwable t) {
    Crashlytics.log(logInfo.getPriority(), logInfo.getTag(), logInfo.getMessage());
    if (t == null) {
      Crashlytics.logException(new Exception(logInfo.getMessage()));
    } else {
      Crashlytics.logException(t);
    }
  }

  @Override
  public void log(@NonNull Map<String, String> logInformation) {
    if (logInformation.isEmpty()) {
      return;
    }

    String exceptionMessage = Stream.of(logInformation)
        .reduce("", (accumulator, entry) -> {
          Crashlytics.setString(entry.getKey(), entry.getValue());
          return accumulator + entry.getKey() + " = " + entry.getValue() + "\n";
        });
    Crashlytics.logException(new Exception(exceptionMessage));
  }
}
