package com.xmartlabs.bigbang.test.assertions

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher

object SwipeToRefreshAssertions {
  /**
   * Performs the `action` using the specified `constraints`
   *
   * @param action the action to be performed
   * @param constraints the set of constraints to take into account
   *
   * @return the [ViewAction] instance that applies `action` under `constraints`
   */
  fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>) = object : ViewAction {
    override fun getConstraints() = constraints
  
    override fun getDescription() = action.description
  
    override fun perform(uiController: UiController, view: View) {
      action.perform(uiController, view)
    }
  }
}
