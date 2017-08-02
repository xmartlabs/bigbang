package com.xmartlabs.bigbang.core.log.analytics

/** Interface to implement to track analytics.  */
interface AnalyticTracker {
  fun track(analyticTrack: TrackableAnalytic<*>)
}
