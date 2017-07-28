package com.xmartlabs.bigbang.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import bullet.ObjectGraph
import com.f2prateek.dart.Dart
import com.trello.rxlifecycle2.components.RxActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.xmartlabs.bigbang.core.Injector

/**
 * A base AppCompatActivity that inherits from [RxActivity] and performs [Dart] and [ObjectGraph]
 * injections in the [.onCreate] lifecycle method.
 */
abstract class BaseAppCompatActivity : RxAppCompatActivity() {
  protected val context: Context
    get() = this

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Dart.inject(this)
    Injector.inject(this)
  }

  /**
   * Removes the given fragment from the view.
   *
   * @param fragment the fragment to be removed
   */
  open fun removeFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().remove(fragment).commit()
  }

  /** Hides the keyboard, if visible.  */
  open protected fun hideKeyboard() {
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = this.currentFocus
    if (currentFocus != null) {
      inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
  }
}
