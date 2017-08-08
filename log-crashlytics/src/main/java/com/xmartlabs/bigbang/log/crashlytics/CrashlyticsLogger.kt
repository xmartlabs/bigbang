package com.xmartlabs.bigbang.log.crashlytics

import android.content.Context

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.xmartlabs.bigbang.core.log.LogInfo
import com.xmartlabs.bigbang.core.log.Logger
import com.xmartlabs.bigbang.core.model.BuildInfo

import io.fabric.sdk.android.Fabric

/** [Logger] that logs exceptions/events to Crashlytics.  */
class CrashlyticsLogger : Logger {
  /** Initializes the [Crashlytics] logger only for release.   */
  fun initialize(buildInfo: BuildInfo, applicationContext: Context): CrashlyticsLogger {
    val crashlyticsCore = CrashlyticsCore.Builder()
        .disabled(buildInfo.isDebug)
        .build()

    val crashlytics = Crashlytics.Builder().core(crashlyticsCore).build()
    Fabric.with(applicationContext, crashlytics)

    return this
  }

  override fun log(logInfo: LogInfo, t: Throwable?) {
    Crashlytics.log(logInfo.priority, logInfo.tag, logInfo.message)
    if (t == null) {
      Crashlytics.logException(Exception(logInfo.message))
    } else {
      Crashlytics.logException(t)
    }
  }

  override fun log(logInformation: Map<String, String>, t: Throwable?) {
    if (logInformation.isEmpty()) {
      return
    }
  
    val exceptionMessage = logInformation.toList().fold("") { acc, (first, second) ->
      Crashlytics.setString(first, second)
      "$acc$first = $second\n"
    }
  
    Crashlytics.logException(t ?: Exception(exceptionMessage))
  }
}
