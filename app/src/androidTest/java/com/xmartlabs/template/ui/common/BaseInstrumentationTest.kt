package com.xmartlabs.template.ui.common

import android.app.Activity
import android.app.Instrumentation
import android.support.annotation.CallSuper
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import com.xmartlabs.template.BaseProjectApplication
import io.appflate.restmock.RESTMockServer
import org.junit.Before
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
abstract class BaseInstrumentationTest {
  lateinit var instrumentation: Instrumentation

  @Before
  @CallSuper
  fun setUp() {
    instrumentation = InstrumentationRegistry.getInstrumentation()
    val app = instrumentation.targetContext.applicationContext as BaseProjectApplication
    app.inject(this)
    RESTMockServer.reset()
  }

  @JvmOverloads protected fun sleep(seconds: Int = 1) {
    try {
      Thread.sleep((seconds * 1000).toLong())
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }

  }

  val activityInstance: Activity
    get() {
      val currentActivity = arrayOfNulls<Activity>(1)
      instrumentation.runOnMainSync {
        val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        if (resumedActivities.iterator().hasNext()) {
          currentActivity[0] = resumedActivities.iterator().next() as Activity
        }
      }

      return currentActivity[0]!!
    }

  @Throws(Throwable::class)
  protected fun assertCurrentActivityIs(activityClass: Class<out Activity>) {
    sleep()
    val currentActivityName = activityInstance.javaClass
    if (activityClass != activityClass) {
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
}