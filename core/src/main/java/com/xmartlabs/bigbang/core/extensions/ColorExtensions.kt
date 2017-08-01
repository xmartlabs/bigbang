package com.xmartlabs.bigbang.core.extensions

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

/** Extension object that contains all related color extensions that make use of [Context] */
val Context.color
  get() = let { _ColorHex.instance.apply { context = it } }

/** Internal class used to extend [Context] inside the `color` scope */
class _ColorHex internal constructor() {
  companion object {
    private var _instance: _ColorHex? = null
    internal val instance: _ColorHex
      get() {
        _instance = _instance ?: _ColorHex()
        return _instance!!
      }
  }
  
  internal lateinit var context: Context
  
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
