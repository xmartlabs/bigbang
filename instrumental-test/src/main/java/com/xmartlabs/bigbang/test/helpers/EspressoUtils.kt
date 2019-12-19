package com.xmartlabs.bigbang.test.helpers

import android.view.View
import android.widget.ImageButton
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.*
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
