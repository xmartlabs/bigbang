package com.xmartlabs.bigbang.ui.extension

import android.support.v4.app.Fragment

/** Hides the keyboard, if visible.  */
fun Fragment.hideKeyboard() {
  activity?.hideKeyboard()
}
