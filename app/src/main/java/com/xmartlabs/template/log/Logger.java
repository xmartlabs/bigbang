package com.xmartlabs.template.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Logger {
  void log(@NonNull LogInfo logInfo, @Nullable Throwable t);
}
