package com.xmartlabs.bigbang.core.extensions

import android.content.res.Resources
import android.content.res.Resources.Theme
import android.support.annotation.AttrRes
import android.support.annotation.DimenRes
import android.support.annotation.Dimension
import android.support.v7.app.ActionBar

/** Extension object that contains all related metric extensions that make use of [Resources]  */
val Resources.metric
  get() = _Metric.instance(this)

/** Internal class used to extend [Resources] inside the `metric` scope */
class _Metric internal constructor() {
  companion object {
    internal val instance by lazy { _Metric() }
  }
  
  internal lateinit var resources: Resources
  
  operator fun invoke(resources: Resources) = apply { this.resources = resources }
  
  /**
   * Converts the `dp` value to pixels dimension.
   * *
   * @param dp the value in dp dimension
   * *
   * @return the converted `dp` value to pixels
   */
  @Dimension(unit = Dimension.PX)
  fun dpToPx(@Dimension(unit = Dimension.DP) dp: Float) = dp * resources.displayMetrics.density
  
  /**
   * Converts the `sp` value to pixels dimension.
   * *
   * @param sp the value in sp dimension
   * *
   * @return the converted `sp` value to pixels
   */
  @Dimension(unit = Dimension.PX)
  fun spToPx(@Dimension(unit = Dimension.SP) sp: Float) = sp * resources.displayMetrics.scaledDensity
  
  /**
   * Converts the `dp` value to pixels dimension.
   * *
   * @param dp the value in dp dimension
   * *
   * @return the converted `dp` value to integer pixels
   */
  @Dimension(unit = Dimension.PX)
  fun dpToPxInt(@Dimension(unit = Dimension.DP) dp: Float) = dpToPx(dp).toInt()
  
  /**
   * Converts the given `dimenId` resource to pixels.
   * *
   * @param dimenId the resource to convert
   * *
   * @return the converted `dimenId` resource value to integer pixels
   */
  @Dimension(unit = Dimension.PX)
  fun dimResToPxInt(@DimenRes dimenId: Int) = resources.getDimension(dimenId).toInt()
  
  /**
   * Converts the `px` value to dp.
   * *
   * @param px the value in pixels to convert to dp
   * *
   * @return the converted `px` value to dp
   */
  @Dimension(unit = Dimension.DP)
  fun pxToDp(@Dimension(unit = Dimension.PX) px: Float) = px / resources.displayMetrics.density
  
  /**
   * Retrieves the toolbar height of the current app theme.
   * *
   * @param theme the [Theme] from which to extract the metric
   * @param actionBarSize the current [ActionBar] size
   * *
   * @return the toolbar height of the current app theme
   */
  @Dimension(unit = Dimension.DP)
  fun getToolbarHeight(theme: Theme, @AttrRes actionBarSize: Int): Int {
    val styledAttributes = theme.obtainStyledAttributes(intArrayOf(actionBarSize))
    val toolbarHeight = styledAttributes.getDimension(0, 0f).toInt()
    styledAttributes.recycle()
    return toolbarHeight
  }
}
