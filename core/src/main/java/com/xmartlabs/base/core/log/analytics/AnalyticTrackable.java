package com.xmartlabs.base.core.log.analytics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

public interface AnalyticTrackable<T> {
  @NonNull
  public String getName();
  @Nullable
  public Map<String, ?> getParameters();
  @NonNull
  public T getAnaliticType();
}
