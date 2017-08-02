package com.xmartlabs.bigbang.ui

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.xmartlabs.bigbang.ui.R.layout.activity_fragment_with_toolbar

import kotlinx.android.synthetic.main.activity_fragment_with_toolbar.*

/** [SingleFragmentActivity] with [Toolbar] support. */
abstract class SingleFragmentWithToolbarActivity : SingleFragmentActivity() {
  protected open var showNavigationIcon = false

  override val layoutResId: Int
    get() = activity_fragment_with_toolbar

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    if (!showNavigationIcon) {
      toolbar.navigationIcon = null
    }

    setSupportActionBar(toolbar)
  }
}
