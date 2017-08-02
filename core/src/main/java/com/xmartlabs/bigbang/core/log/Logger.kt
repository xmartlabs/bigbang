package com.xmartlabs.bigbang.core.log

/** Interface that defines a type that can log any type of [Throwable] exception.  */
interface Logger {
  /**
   * Logs a [Throwable] exception or an event that, while not an exception, is a deviation from the normal
   * course of events.
   * @param logInfo the information associated with the event/exception
   * *
   * @param t the exception, if present
   */
  fun log(logInfo: LogInfo, t: Throwable?)

  /**
   * Logs a set of information related to an event that deviates from the normal course of events.
   * @param logInformation the information to be logged
   * *
   * @param t The captured exception, if present
   */
  fun log(logInformation: Map<String, String>, t: Throwable?)
}
