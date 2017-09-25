package com.xmartlabs.bigbang.test.extensions

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import android.view.View
import org.hamcrest.Matcher

object SwipeToRefreshAssertions {
  // http://stackoverflow.com/a/33516360/5170805
  fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
    return object : ViewAction {
      override fun getConstraints(): Matcher<View> = constraints

      override fun getDescription(): String = action.description

      override fun perform(uiController: UiController, view: View) {
        action.perform(uiController, view)
      }
    }
  }
}

@Suppress("unused")
fun ViewInteraction.performPullDownToRefresh(): ViewInteraction =
    perform(SwipeToRefreshAssertions.withCustomConstraints(ViewActions.swipeDown(), isDisplayingAtLeast(85)))

