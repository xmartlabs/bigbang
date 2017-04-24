package com.xmartlabs.base.core.log.analytics;

import android.support.annotation.NonNull;

/** Interface to implement to track analytics. */
public interface AnalyticTracker {
  void track(@NonNull TrackableAnalytic analyticTrack);
}
