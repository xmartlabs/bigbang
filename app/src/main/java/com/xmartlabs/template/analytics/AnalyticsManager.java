package com.xmartlabs.template.analytics;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;
import com.xmartlabs.base.core.log.analytics.AnalyticTrackable;
import com.xmartlabs.base.core.log.analytics.AnalyticTracker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public final class AnalyticsManager {
  @NonNull
  private List<AnalyticTracker> analyticTrackers = new ArrayList<>();

  @Inject
  AnalyticsManager() {}

  public void track(@NonNull AnalyticTrackable analyticTrackable) {
    Stream.of(analyticTrackers)
        .forEach(analyticTracker -> analyticTracker.track(analyticTrackable));
  }

  public void addTracker(AnalyticTracker analyticTracker) {
    analyticTrackers.add(analyticTracker);
  }

  public void removeTracker(AnalyticTracker analyticTracker) {
    analyticTrackers.remove(analyticTracker);
  }
}
