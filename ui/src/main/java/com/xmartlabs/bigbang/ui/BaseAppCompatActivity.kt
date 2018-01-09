package com.xmartlabs.bigbang.ui

import android.content.Context
import android.os.Bundle
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
}
