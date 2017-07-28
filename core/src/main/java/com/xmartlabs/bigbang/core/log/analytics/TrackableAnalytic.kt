package com.xmartlabs.bigbang.core.log.analytics

/** Trackable analytic that represents something to be tracked. For example: an event, a screen, others...  */
interface TrackableAnalytic<out T> {
  /** @return string the trackable analytic name
   */
  val name: String

  /** @return `Map<String, Any>` parameters that this trackable analytic should track
   */
  val parameters: Map<String, Any>?

  /** @return `<T>` Analityc type. The type of analytic to track, such as Event, Screen or other type
   */
  val analiticType: T
}
