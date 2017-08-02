package com.xmartlabs.bigbang.core.log.analytics

import javax.inject.Inject

/** Manager of all the analytic trackers the app will use.  */
class AnalyticsManager @Inject
internal constructor() {
  private val analyticTrackers = ArrayList<AnalyticTracker>()

  /**
   * Tracks the analytic that wants to be tracked in all the trackers added to the analyticTrackers list.
   * @param trackableAnalytic Any event, screen or whatever the app wants to track
   */
  fun track(trackableAnalytic: TrackableAnalytic<*>) = analyticTrackers.forEach { it.track(trackableAnalytic) }

  fun addTracker(analyticTracker: AnalyticTracker) = analyticTrackers.add(analyticTracker)

  fun removeTracker(analyticTracker: AnalyticTracker) = analyticTrackers.remove(analyticTracker)
}
