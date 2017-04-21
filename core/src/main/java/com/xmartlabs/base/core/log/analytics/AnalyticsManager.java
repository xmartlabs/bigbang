package com.xmartlabs.base.core.log.analytics;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/** Manager of all the analytic trackers the app will use. */
public final class AnalyticsManager {
  @NonNull
  private List<AnalyticTracker> analyticTrackers = new ArrayList<>();

  @Inject
  AnalyticsManager() {}

  /**
   *  Tracks the analytic that wants to be tracked in all the trackers added to the analyticTrackers list.
   *
   * @param trackableAnalytic Any event, screen or whatever the app wants to track
   */
  public void track(@NonNull TrackableAnalytic trackableAnalytic) {
    Stream.of(analyticTrackers)
        .forEach(analyticTracker -> analyticTracker.track(trackableAnalytic));
  }

  public void addTracker(AnalyticTracker analyticTracker) {
    analyticTrackers.add(analyticTracker);
  }

  public void removeTracker(AnalyticTracker analyticTracker) {
    analyticTrackers.remove(analyticTracker);
  }
}
