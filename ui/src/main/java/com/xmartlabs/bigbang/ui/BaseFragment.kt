package com.xmartlabs.bigbang.ui

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.trello.rxlifecycle2.components.support.RxFragment
import com.xmartlabs.bigbang.core.di.Injectable
import com.xmartlabs.bigbang.core.log.analytics.AnalyticsManager
import com.xmartlabs.bigbang.core.log.analytics.TrackableAnalytic
import com.xmartlabs.bigbang.ui.common.BaseProgressDialog
import com.xmartlabs.bigbang.ui.extension.removeFragment
import javax.inject.Inject

/**
 * Base Fragment implementation with the following functionality:
 *
 *  * Inflate the view given a layout resource
 *  * Bind the view layout elements with ButterKnife
 *  * Ability to show/hide a progress dialog of your choosing (providing it extends from [BaseProgressDialog]
 *  * If the activity that holds this Fragment extends from [BaseAppCompatActivity], allows the instance
 * to be removed
 *  * Ability to remove itself from parent fragment
 *  * Proper cleanup on detach/destroy
 *
 */
abstract class BaseFragment : RxFragment(), Injectable {
  @Inject
  internal lateinit var analyticsManager: AnalyticsManager
  
  /** Create the trackable analytic to be tracked  */
  open protected val screenTrackableAnalytic: TrackableAnalytic<*>?
    get() = null
  
  /**
   * Inflates the view layout/elements.
   * @return the layout resource from which to inflate the view
   */
  @get:LayoutRes
  protected abstract val layoutResId: Int
  
  private var progressDialog: BaseProgressDialog? = null
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FragmentArgs.inject(this)
  }
  
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
      inflater.inflate(layoutResId, container, false)

  @Suppress("OverridingDeprecatedMember", "DEPRECATION")
  override fun onAttach(activity: Activity?) {
    super.onAttach(activity)
    progressDialog = createProgressDialog()
  }
  
  /**
   * If there is any analytic tracker configured and the trackable analytic
   * created, it will track that this screen was displayed
   */
  open protected fun trackViewIfNeeded() = screenTrackableAnalytic?.let(analyticsManager::track)
  
  /**
   * Creates a BaseProgressDialog instance to be used to show/hide a progress dialog.
   * The dialog must extend from BaseProgressDialog.
   * @return the BaseProgressDialog instance to be shown upon request
   */
  open protected fun createProgressDialog(): BaseProgressDialog? = null
  
  override fun onDetach() {
    super.onDetach()
    progressDialog?.dismiss()
    progressDialog = null
  }
  
  /**
   * Show a simple alert with an ok button.
   * @param stringResId the message to be shown in the alert
   */
  open protected fun showAlertError(stringResId: Int) = context?.let {
    AlertDialog.Builder(it)
        .setMessage(stringResId)
        .setNeutralButton(android.R.string.ok, null)
        .show()
  }
  
    /** Shows a progress dialog, provided the method [.createProgressDialog] return an object that isn't null.  */
    open fun showProgressDialog() = progressDialog?.show()
  
    /** Hides the progress dialog shown with [.showProgressDialog], if such a dialog was ever shown (and exists).  */
    open fun hideProgressDialog() = progressDialog?.hide()
  
    /** Removes the Fragment from the [BaseAppCompatActivity] Activity.  */
    open protected fun removeItselfFromActivity() = (activity as? BaseAppCompatActivity)?.removeFragment(this)
  
    /** Removes the Fragment from its parent  */
    open protected fun removeItselfFromParentFragment() {
      parentFragment?.childFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
  
    /**
     * If the Fragment is contained within another Fragment, it removes the former.
     * Otherwise, removes the Fragment from the [BaseAppCompatActivity] Activity.
     */
    open protected fun removeItselfFromParent() =
        if (parentFragment != null) removeItselfFromParentFragment() else removeItselfFromActivity()
}
