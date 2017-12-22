package com.xmartlabs.bigbang.test.helpers

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isClickable
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageButton
import org.hamcrest.Matchers.allOf

/**
 * Utility class for managing Espresso views
 */
class EspressoUtils {
  companion object {
    /**
     * Retrieves the [ViewInteraction] of the up button
     *
     * @return the [ViewInteraction] instance
     */
    @JvmStatic
    fun onUpButtonView() = onView(
        allOf<View>(isAssignableFrom(ImageButton::class.java),
            withParent(isAssignableFrom(Toolbar::class.java)),
            isClickable()))
  
    /**
     * Creates a [ViewInteraction] for the view with the given resource id, if found
     *
     * @param id the views' resource id
     *
     * @return the [ViewInteraction] instance
     */
    @JvmStatic
    fun onViewWithId(@IdRes id: Int) = onView(withId(id))
  
    /**
     * Creates a [ViewInteraction] for the view that is displaying the text with the resource `id`
     *
     * @param textResId the id of the text resource to find
     *
     * @return the [ViewInteraction] instance
     */
    @JvmStatic
    fun onViewWithText(@StringRes textResId: Int) = onView(withText(textResId))
  
    /**
     * Creates a [ViewInteraction] for the TextView whose text property value matches `text`
     *
     * @return the [ViewInteraction] instance
     */
    @JvmStatic
    fun onViewWithText(text: String) = onView(withText(text))
  }
}
