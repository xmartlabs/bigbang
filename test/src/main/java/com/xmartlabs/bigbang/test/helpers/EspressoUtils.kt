package com.xmartlabs.bigbang.test.helpers

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageButton
import org.hamcrest.Matchers.allOf

class EspressoUtils {
  companion object {
    @JvmStatic
    fun onUpButtonView(): ViewInteraction = onView(
        allOf<View>(isAssignableFrom(ImageButton::class.java),
            withParent(isAssignableFrom(Toolbar::class.java)),
            isClickable()))

    @JvmStatic
    fun onViewWithId(@IdRes id: Int): ViewInteraction = onView(withId(id))

    @JvmStatic
    fun onViewWithText(@StringRes textResId: Int): ViewInteraction = onView(withText(textResId))

    @JvmStatic
    fun onViewWithText(text: String): ViewInteraction = onView(withText(text))
  }
}
