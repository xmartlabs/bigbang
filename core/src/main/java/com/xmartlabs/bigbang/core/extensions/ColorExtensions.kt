package com.xmartlabs.bigbang.core.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/** Extension object that contains all related color extensions that make use of [Context] */
val Context.color
  get() = _ColorHex.instance(this)

/** Internal class used to extend [Context] inside the `color` scope */
class _ColorHex internal constructor() {
  companion object {
    internal val instance by lazy { _ColorHex() }
  }
  
  internal lateinit var context: Context
  
  operator fun invoke(context: Context) = apply { this.context = context }
  
  /**
   * Retrieves the Hex string representation of the color resource.
   *
   * @param colorResId the id of the color resource
   *
   * @return the Hex string representation of the color resource
   */
  fun getResourceColorHexString(@ColorRes colorResId: Int) : String {
    val color = ContextCompat.getColor(context, colorResId)
    return String.format("#%06X", 0xFFFFFF and color)
  }
}
