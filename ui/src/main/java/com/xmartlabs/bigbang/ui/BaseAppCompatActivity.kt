package com.xmartlabs.bigbang.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.f2prateek.dart.Dart
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * A base AppCompatActivity that inherits from [RxActivity] and performs [Dart]
 * injections in the [.onCreate] lifecycle method.
 */
abstract class BaseAppCompatActivity : RxAppCompatActivity(), HasSupportFragmentInjector {
  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

  protected val context: Context
    get() = this

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Dart.inject(this)
  }

  override fun supportFragmentInjector() = dispatchingAndroidInjector
}
