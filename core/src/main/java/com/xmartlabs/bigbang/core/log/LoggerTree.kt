package com.xmartlabs.bigbang.core.log

import android.util.Log
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Subclass of [Timber.DebugTree] that allows the captured exception/events to be logged with any user-defined
 * Logger, such as Crashlytics, Sentry, etc.
 */
class LoggerTree @Inject
constructor() : Timber.DebugTree() {
  /** [Logger] instances that can log any captured exception/event.  */
  private val loggers = ArrayList<Logger>()
  /** [Log] priorities that will be excluding from logging  */
  private val excludedPriorities = ArrayList<Int>()

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) = when {
    excludedPriorities.contains(priority) -> Unit
    else -> {
      val logInfo = LogInfo(priority, tag, message)
      loggers.forEach { it.log(logInfo, t) }
    }
  }

  /**
   * Logs a set of information related to an event that deviates from the normal course of events.
   *
   * @param logInformation the information to be logged
   */
  fun log(logInformation: Map<String, String>, t: Throwable?) = loggers.forEach { it.log(logInformation, t) }

  /**
   * Adds a new [Logger].
   *
   * @param logger [Logger] to be added
   */
  fun addLogger(logger: Logger) = apply { loggers.add(logger) }

  /**
   * Removes a [Logger], so no new events will be processed by it.
   *
   * @param logger the [Logger] to be removed
   */
  fun removeLogger(logger: Logger) = apply { loggers.remove(logger) }

  /**
   * Adds a priority to be excluded from logging.
   *
   * @param priority the priority to be excluded
   */
  fun excludeLogPriority(priority: Int) = apply { excludedPriorities.add(priority) }

  /**
   * Removes the `priority` from the exclusion list. Thus, all the new events with the priority will be logged.
   *
   * @param priority the priority whose events are to be logged
   */
  fun includeLogPriority(priority: Int) = apply { excludedPriorities.removeAt(priority) }
}
