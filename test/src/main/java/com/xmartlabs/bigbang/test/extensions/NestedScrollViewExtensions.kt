package com.xmartlabs.bigbang.test.extensions

import android.graphics.Rect
import android.support.test.espresso.PerformException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.util.HumanReadables
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf

class ScrollToActionForNestedScrollView : ViewAction {
  companion object {
    private val TAG = ScrollToActionForNestedScrollView::class.java.simpleName
    private const val DISPLAY_PERCENTAGE = 90
  }

  override fun getConstraints(): Matcher<View> {
    return allOf<View>(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), isDescendantOfA(anyOf<View>(
        isAssignableFrom(NestedScrollView::class.java), isAssignableFrom(NestedScrollView::class.java))))
  }

  override fun perform(uiController: UiController, view: View) {
    if (isDisplayingAtLeast(DISPLAY_PERCENTAGE).matches(view)) {
      Log.i(TAG, "View is already displayed. Returning.")
      return
    }
    val rect = Rect()
    view.getDrawingRect(rect)
    if (!view.requestRectangleOnScreen(rect, true)) {
      Log.w(TAG, "Scrolling to view was requested, but none of the parents scrolled.")
    }
    uiController.loopMainThreadUntilIdle()
    if (!isDisplayingAtLeast(DISPLAY_PERCENTAGE).matches(view)) {
      throw PerformException.Builder()
          .withActionDescription(this.description)
          .withViewDescription(HumanReadables.describe(view))
          .withCause(RuntimeException("Scrolling to view was attempted, but the view is not displayed"))
          .build()
    }
  }

  override fun getDescription(): String = "scroll to"
}

fun nestedScrollViewScrollTo(): ViewAction {
  return android.support.test.espresso.action.ViewActions.actionWithAssertions(
      ScrollToActionForNestedScrollView())
}

fun ViewInteraction.performScrollInNestedScrollView() : ViewInteraction = perform(nestedScrollViewScrollTo())