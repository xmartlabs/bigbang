package com.xmartlabs.base.log.crashlytics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.xmartlabs.base.core.log.LogInfo;
import com.xmartlabs.base.core.log.Logger;
import com.xmartlabs.base.core.model.BuildInfo;

import java.util.Map;

import io.fabric.sdk.android.Fabric;

/** {@link Logger} that logs exceptions/events to Crashlytics. **/
public class CrashlyticsLogger implements Logger {
  /** Initializes the {@link Crashlytics} logger only for release.  */
  public CrashlyticsLogger initialize(@NonNull BuildInfo buildInfo, @NonNull Context applicationContext) {
    CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
        .disabled(buildInfo.isDebug())
        .build();

    Crashlytics crashlytics = new Crashlytics.Builder().core(crashlyticsCore).build();
    Fabric.with(applicationContext, crashlytics);

    return this;
  }

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
  public void log(@NonNull Map<String, String> logInformation, @Nullable Throwable t) {
    if (logInformation.isEmpty()) {
      return;
    }

    String exceptionMessage = Stream.of(logInformation)
        .reduce("", (accumulator, entry) -> {
          Crashlytics.setString(entry.getKey(), entry.getValue());
          return accumulator + entry.getKey() + " = " + entry.getValue() + "\n";
        });
    Optional.ofNullable(t)
        .executeIfAbsent(() -> Crashlytics.logException(new Exception(exceptionMessage)))
        .ifPresent(throwable -> Crashlytics.logException(t));
  }
}
