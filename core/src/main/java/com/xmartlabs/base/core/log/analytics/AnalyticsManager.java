package com.xmartlabs.base.core.log.analytics;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public final class AnalyticsManager {
  @NonNull
  private List<AnalyticTracker> analyticTrackers = new ArrayList<>();

  @Inject
  AnalyticsManager() {}

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
