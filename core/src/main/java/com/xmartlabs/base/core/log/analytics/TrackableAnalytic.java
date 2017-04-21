package com.xmartlabs.base.core.log.analytics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

/** Trackable analytic that represents something to be tracked. For example: an event, a screen, others... */
public interface TrackableAnalytic<T> {
  /** @return string the trackable analytic name */
  @NonNull
  String getName();

  /** @return {@code Map<String, ?>} parameters that this trackable analytic should track */
  @Nullable
  Map<String, ?> getParameters();

  /** @return {@code <T>} Analityc type. The type of analytic to track, such as Event, Screen or other type */
  @NonNull
  T getAnaliticType();
}
