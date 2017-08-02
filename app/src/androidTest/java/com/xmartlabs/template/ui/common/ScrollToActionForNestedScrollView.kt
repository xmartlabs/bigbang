package com.xmartlabs.template.ui.common

import android.graphics.Rect
import android.support.test.espresso.PerformException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.util.HumanReadables
import android.support.v4.widget.NestedScrollView
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import timber.log.Timber

/**
 * Copied from ScrollToAction
 */
class ScrollToActionForNestedScrollView : ViewAction {
  companion object {
    private val TAG = ScrollToActionForNestedScrollView::class.java.simpleName
  }
  
  override fun getConstraints(): Matcher<View> {
    return allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), isDescendantOfA(anyOf(
        isAssignableFrom(NestedScrollView::class.java), isAssignableFrom(NestedScrollView::class.java))))
  }

  override fun perform(uiController: UiController, view: View) {
    if (isDisplayingAtLeast(90).matches(view)) {
      Timber.i(TAG, "View is already displayed. Returning.")
      return
    }
    val rect = Rect()
    view.getDrawingRect(rect)
    if (!/* immediate */view.requestRectangleOnScreen(rect, true)) {
      Timber.w(TAG, "Scrolling to view was requested, but none of the parents scrolled.")
    }
    uiController.loopMainThreadUntilIdle()
    if (!isDisplayingAtLeast(90).matches(view)) {
      throw PerformException.Builder()
          .withActionDescription(this.description)
          .withViewDescription(HumanReadables.describe(view))
          .withCause(RuntimeException("Scrolling to view was attempted, but the view is not displayed"))
          .build()
    }
  }

  override fun getDescription() = "scroll to"
}
