package com.xmartlabs.bigbang.test.viewaction

import android.graphics.Rect
import android.support.test.espresso.PerformException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA
import android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.util.HumanReadables
import android.support.v4.widget.NestedScrollView
import android.view.View
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import timber.log.Timber

/**
 * [ViewAction] that scrolls the [NestedScrollView] to any desired view
 */
class NestedScrollViewViewAction : ViewAction {
  companion object {
    private const val DISPLAY_PERCENTAGE = 90
  
    /**
     * Factory method that performs all assertions before the [ViewAction] and then returns it
     *
     * @return the [NestedScrollViewViewAction] instance
     */
    @JvmStatic
    fun scrollTo() = ViewActions.actionWithAssertions(NestedScrollViewViewAction())
  }

  override fun getConstraints() = allOf<View>(
      withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
      isDescendantOfA(anyOf<View>(
          isAssignableFrom(NestedScrollView::class.java), isAssignableFrom(NestedScrollView::class.java))
      )
  )

  override fun perform(uiController: UiController, view: View) {
    if (isDisplayingAtLeast(DISPLAY_PERCENTAGE).matches(view)) {
      Timber.i("View is already displayed. Returning.")
      return
    }
    val rect = Rect()
    view.getDrawingRect(rect)
    if (!view.requestRectangleOnScreen(rect, true)) {
      Timber.w("Scrolling to view was requested, but none of the parents scrolled.")
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

  override fun getDescription() = "scroll to"
}
