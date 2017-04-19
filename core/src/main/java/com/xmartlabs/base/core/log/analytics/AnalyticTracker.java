package com.xmartlabs.base.core.log.analytics;

import android.support.annotation.NonNull;

/** Interface that defines function to track analytics. */
public interface AnalyticTracker {
  public void track(@NonNull AnalyticTrackable analyticTrack);
}
