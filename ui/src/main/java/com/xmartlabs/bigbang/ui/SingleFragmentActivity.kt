package com.xmartlabs.bigbang.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import com.xmartlabs.bigbang.ui.R.layout.activity_fragment

/**
 * An extension of [BaseAppCompatActivity] that holds a single [Fragment].
 *
 * The fragment is to be created through [.createFragment] method, and will be added in the
 * [.onCreate] lifecycle method.
 */
abstract class SingleFragmentActivity : BaseAppCompatActivity() {
  /**
   * Creates the [Fragment] held and shown by this Activity.
   * @return the Fragment instance to be shown
   */
  protected abstract fun createFragment(): BaseFragment

  /**
   * Specifies the layout of the Activity.
   * When overriding this method, be sure to include a container view in the layout with the id `fragment_container`,
   * otherwise the Activity won't be able to add the Fragment in a proper way.
   * @return the layout resource id of the Activity
   */
  open protected val layoutResId: Int
    @LayoutRes
    get() = activity_fragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(layoutResId)

    var fragment = singleFragment
    if (fragment == null) {
      fragment = createFragment()
      supportFragmentManager
          .beginTransaction()
          .add(R.id.fragment_container, fragment)
          .commit()
    }
  }

  /**
   * Retrieves the currently held Fragment.
   * @return the Fragment held by this Activity
   */
  open protected val singleFragment: Fragment?
    get() {
      val fragmentManager = supportFragmentManager
      return fragmentManager.findFragmentById(R.id.fragment_container)
    }
}
