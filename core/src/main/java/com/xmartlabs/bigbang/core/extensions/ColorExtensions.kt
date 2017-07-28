package com.xmartlabs.bigbang.core.extensions

import android.content.Context
import android.support.annotation.ColorRes

/**
 * Retrieves the Hex string representation of the color resource.
 *
 * @param colorResId the id of the color resource
 * *
 * @return the Hex string representation of the color resource
 */
fun getResourceColorHexString(context: Context, @ColorRes colorResId: Int): String {
  @Suppress("DEPRECATION")
  val color = context.resources.getColor(colorResId)
  return String.format("#%06X", 0xFFFFFF and color)
}