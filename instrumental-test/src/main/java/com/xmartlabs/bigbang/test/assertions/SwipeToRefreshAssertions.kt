package com.xmartlabs.bigbang.test.assertions

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher

object SwipeToRefreshAssertions {
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
