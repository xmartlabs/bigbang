package com.xmartlabs.bigbang.ui.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


/** Hides the keyboard, if visible.  */
fun Activity.hideKeyboard() {
  val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  currentFocus?.let { inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0) }
}

/**
 * Removes the given fragment from the view.
 *
 * @param fragment the fragment to be removed
 */
fun AppCompatActivity.removeFragment(fragment: Fragment) {
  supportFragmentManager.beginTransaction().remove(fragment).commit()
}

/**
 * Opens the intent from the activity context.
 *
 * @param activity the to use for opening the intent
 */
fun Intent.startFrom(activity: Activity) = activity.startActivity(this)
