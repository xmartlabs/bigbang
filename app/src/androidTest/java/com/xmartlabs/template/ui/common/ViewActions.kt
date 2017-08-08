package com.xmartlabs.template.ui.common

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View

import org.hamcrest.Matcher

import android.support.test.espresso.action.ViewActions as SupportViewActions

object ViewActions {
  fun nestedScrollViewScrollTo() = SupportViewActions.actionWithAssertions(ScrollToActionForNestedScrollView())

  // http://stackoverflow.com/a/33516360/5170805
  fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>) = object : ViewAction {
    override fun getConstraints() = constraints
  
    override fun getDescription() = action.description
  
    override fun perform(uiController: UiController, view: View) = action.perform(uiController, view)
  }
}
