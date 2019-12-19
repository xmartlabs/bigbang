package com.xmartlabs.bigbang.ui.extension

import androidx.fragment.app.Fragment

/** Hides the keyboard, if visible.  */
fun Fragment.hideKeyboard() {
  activity?.hideKeyboard()
}
