package com.xmartlabs.bigbang.test.extensions

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageButton
import org.hamcrest.Matchers.allOf

fun ViewInteraction.getUpButtonViewInteraction(): ViewInteraction {
  return onView(allOf<View>(isAssignableFrom(ImageButton::class.java), withParent(isAssignableFrom(Toolbar::class.java)),
      isClickable()))
}