package com.xmartlabs.bigbang.core.extensions

import android.content.res.Resources
import android.content.res.Resources.Theme
import android.support.annotation.AttrRes
import android.support.annotation.Dimension
import android.support.annotation.VisibleForTesting
import android.support.v7.app.ActionBar

@VisibleForTesting
var displayMetrics = Resources.getSystem().displayMetrics

/**
 * Converts the `dp` value to pixels dimension.
 *
 * @return the converted `dp` value to integer pixels
 */
val Int.dpToPx: Int
  @Dimension(unit = Dimension.PX) get() = (this * displayMetrics.density).toInt()

/**
 * Converts the `px` value to dp.
 *
 * @return the converted `px` value to dp
 */
val Int.pxToDp: Int
  @Dimension(unit = Dimension.DP) get() = (this / displayMetrics.density).toInt()

/**
 * Converts the `sp` value to pixels dimension.
 *
 * @return the converted `sp` value to pixels
 */
val Int.spToPx: Int
  @Dimension(unit = Dimension.SP) get() = (this * displayMetrics.scaledDensity).toInt()

/**
 * Converts the `dp` value to pixels dimension.
 *
 * @return the converted `dp` value to integer pixels
 */
val Float.dpToPx: Float
  @Dimension(unit = Dimension.PX) get() = (this * displayMetrics.density)

/**
 * Converts the `px` value to dp.
 *
 * @return the converted `px` value to dp
 */
val Float.pxToDp: Float
  @Dimension(unit = Dimension.DP) get() = (this / displayMetrics.density)

/**
 * Converts the `sp` value to pixels dimension.
 *
 * @return the converted `sp` value to pixels
 */
val Float.spToPx: Float
  @Dimension(unit = Dimension.SP) get() = (this * displayMetrics.scaledDensity)

/**
 * Retrieves the toolbar height of an app theme.
 *
 * @param actionBarSize the current [ActionBar] size
 *
 * @return the toolbar height of the current app theme
 */
@Dimension(unit = Dimension.DP)
fun Theme.toolbarHeight(@AttrRes actionBarSize: Int): Int {
  val styledAttributes = obtainStyledAttributes(intArrayOf(actionBarSize))
  val toolbarHeight = styledAttributes.getDimension(0, 0f).toInt()
  styledAttributes.recycle()
  return toolbarHeight
}
