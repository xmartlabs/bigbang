package com.xmartlabs.bigbang.ui

import android.content.Context
import android.os.Bundle
import com.f2prateek.dart.Dart
import com.trello.rxlifecycle2.components.RxActivity

/** A base Activity that inherits from [RxActivity] and performs [Dart] injection in the [.onCreate] lifecycle method. */
abstract class BaseActivity : RxActivity() {
  protected val context: Context
    get() = this

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Dart.inject(this)
  }
}
