package com.xmartlabs.template.ui.common

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import android.support.v7.widget.Toolbar
import android.widget.ImageButton
import com.xmartlabs.bigbang.core.extensions.ignoreException
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
abstract class SingleActivityInstrumentationTest<T : Activity> : BaseInstrumentationTest() {
  @Rule
  @JvmField
  var activityTestRule = createTestRule()
  
  protected val defaultIntent: Intent? = null
  
  protected fun createTestRule(): ActivityTestRule<T> {
    return IntentsTestRule(activityClass, true, false)
  }

  @Test
  fun checkActivityOpens() {
    launchActivityWithDefaultIntent()
  }

  @Test
  fun checkUpNavigation() {
    launchActivityWithDefaultIntent()
    upButtonViewInteraction.ignoreException { perform(click()) }
  }

  protected val upButtonViewInteraction: ViewInteraction
    get() = onView(allOf(isAssignableFrom(ImageButton::class.java), withParent(isAssignableFrom(Toolbar::class.java)),
        isClickable()))

  protected fun launchActivityWithDefaultIntent() {
    activityTestRule.launchActivity(defaultIntent)
  }
  
  protected fun allowPermissionsIfNeeded() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val device = UiDevice.getInstance(instrumentation)
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
  
  protected abstract val activityClass: Class<T>
}
