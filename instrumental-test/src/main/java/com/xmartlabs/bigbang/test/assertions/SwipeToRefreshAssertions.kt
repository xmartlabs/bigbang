package com.xmartlabs.bigbang.test.assertions

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
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
