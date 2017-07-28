package com.xmartlabs.bigbang.core.extensions

import android.content.res.Resources
import android.content.res.Resources.Theme
import android.support.annotation.AttrRes
import android.support.annotation.DimenRes
import android.support.annotation.Dimension
import android.support.v7.app.ActionBar

/**
 * Converts the `dp` value to pixels dimension.
 * *
 * @param dp the value in dp dimension
 * *
 * @return the converted `dp` value to pixels
 */
@Dimension(unit = Dimension.PX)
fun Resources.dpToPx(@Dimension(unit = Dimension.DP) dp: Float) = dp * this.displayMetrics.density

/**
 * Converts the `sp` value to pixels dimension.
 * *
 * @param sp the value in sp dimension
 * *
 * @return the converted `sp` value to pixels
 */
@Dimension(unit = Dimension.PX)
fun Resources.spToPx(@Dimension(unit = Dimension.SP) sp: Float) = sp * this.displayMetrics.scaledDensity

/**
 * Converts the `dp` value to pixels dimension.
 * *
 * @param dp the value in dp dimension
 * *
 * @return the converted `dp` value to integer pixels
 */
@Dimension(unit = Dimension.PX)
fun Resources.dpToPxInt(@Dimension(unit = Dimension.DP) dp: Float) = dpToPx(dp).toInt()

/**
 * Converts the given `dimenId` resource to pixels.
 * *
 * @param dimenId the resource to convert
 * *
 * @return the converted `dimenId` resource value to integer pixels
 */
@Dimension(unit = Dimension.PX)
fun Resources.dimResToPxInt(@DimenRes dimenId: Int) = this.getDimension(dimenId).toInt()

/**
 * Converts the `px` value to dp.
 * *
 * @param px the value in pixels to convert to dp
 * *
 * @return the converted `px` value to dp
 */
@Dimension(unit = Dimension.DP)
fun Resources.pxToDp(@Dimension(unit = Dimension.PX) px: Float) = px / this.displayMetrics.density

/**
 * Retrieves the toolbar height of the current app theme.
 * *
 * @param actionBarSize the current [ActionBar] size
 * *
 * @return the toolbar height of the current app theme
 */
@Dimension(unit = Dimension.DP)
fun Theme.getToolbarHeight(@AttrRes actionBarSize: Int): Int {
  val styledAttributes = this.obtainStyledAttributes(intArrayOf(actionBarSize))
  val toolbarHeight = styledAttributes.getDimension(0, 0f).toInt()
  styledAttributes.recycle()
  return toolbarHeight
}
