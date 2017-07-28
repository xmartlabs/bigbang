package com.xmartlabs.template.ui.common

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View

import org.hamcrest.Matcher

object ViewActions {
  fun nestedScrollViewScrollTo(): ViewAction {
    return android.support.test.espresso.action.ViewActions.actionWithAssertions(
        ScrollToActionForNestedScrollView())
  }

  // http://stackoverflow.com/a/33516360/5170805
  fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
    return object : ViewAction {
      override fun getConstraints(): Matcher<View> {
        return constraints
      }

      override fun getDescription(): String {
        return action.description
      }

      override fun perform(uiController: UiController, view: View) {
        action.perform(uiController, view)
      }
    }
  }
}
