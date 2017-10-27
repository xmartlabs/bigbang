package com.xmartlabs.bigbang.test.extensions

import android.app.Activity
import android.app.Instrumentation
import android.os.Build
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.xmartlabs.bigbang.test.helpers.TestUtils
import timber.log.Timber
import java.util.Locale

/**
 * Retrieves the first activity with `Stage.RESUMED` state
 *
 * @return the first activity with `Stage.RESUMED` state, if any
 */
fun Instrumentation.getFirstActivityInstance(): Activity? {
  var currentActivity: Activity? = null
  
  runOnMainSync {
    currentActivity = ActivityLifecycleMonitorRegistry.getInstance()
        .getActivitiesInStage(Stage.RESUMED)
        .first()
  }

  return currentActivity
}

/**
 * Accepts any permission request by accepting the Android popup dialog with the request
 */
fun Instrumentation.allowPermissionsIfNeeded() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    val device = UiDevice.getInstance(this)
    val allowPermissions = device.findObject(UiSelector().clickable(true).index(1))
    if (allowPermissions.exists()) {
      try {
        allowPermissions.click()
      } catch (e: UiObjectNotFoundException) {
        Timber.e(e, "There is no permissions dialog to interact with ")
      }
    }
  }
}

/**
 * Asserts that the current activity is of class `activityClass`
 *
 * @param activityClass the class of the current activity
 */
@Throws(RuntimeException::class)
fun Instrumentation.assertCurrentActivityIs(activityClass: Class<out Activity>) {
  TestUtils.sleep()
  val currentActivityName = getFirstActivityInstance()?.javaClass
  if (activityClass != currentActivityName) {
    throw IllegalStateException(
        String.format(
            Locale.US,
            "Expected %s but the current activity is %s",
            activityClass,
            currentActivityName
        )
    )
  }
}
