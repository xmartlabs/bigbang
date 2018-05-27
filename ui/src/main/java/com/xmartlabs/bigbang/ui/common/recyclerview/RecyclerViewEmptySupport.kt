package com.xmartlabs.bigbang.ui.common.recyclerview

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

import com.xmartlabs.bigbang.ui.R

/**
 * [RecyclerView] subclass that automatically handles empty state.
 *
 * A [RecyclerView] is in an empty state when its adapter holds zero items,
 * or if the callback function [.isInEmptyState] is set and returns true.
 *
 * In the empty state, the [] will be hidden and a view will be shown.
 * The empty state view to be shown can be defined in two ways:
 *
 *  1. By means of [.setEmptyView] method
 *  2. By setting the attribute `app:emptyViewId` in the recycler view and point to a view in the hierarchy
 */
class RecyclerViewEmptySupport @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {
  private var emptyView: View? = null
  @IdRes
  private var emptyViewId: Int = 0
  private var isInEmptyState: ((RecyclerView) -> Boolean)? = null

  private val emptyObserver = object : RecyclerView.AdapterDataObserver() {
    override fun onChanged() {
      super.onChanged()
      showCorrectView()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
      super.onItemRangeInserted(positionStart, itemCount)
      showCorrectView()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
      super.onItemRangeRemoved(positionStart, itemCount)
      showCorrectView()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
      super.onItemRangeChanged(positionStart, itemCount)
      showCorrectView()
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
      super.onItemRangeMoved(fromPosition, toPosition, itemCount)
      showCorrectView()
    }
  }

  init {
    bindAttributes(context, attrs)
    initializeEmptyView()
  }

  /**
   * Extracts the resource id from the [RecyclerView] attributes, if present.
   *
   * @param context The Context the view is running in, through which it can
   * *        access the current theme, resources, etc.
   * *
   * @param attrs The attributes of the XML tag that is inflating the view.
   */
  private fun bindAttributes(context: Context, attrs: AttributeSet?) {
    val attributes = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewEmptySupport, 0, 0)
    emptyViewId = attributes.getResourceId(R.styleable.RecyclerViewEmptySupport_emptyView, -1)
    attributes.recycle()
  }

  /** Initializes the empty view using the resource identifier [.emptyViewId], if it exists.  */
  private fun initializeEmptyView() {
    if (emptyViewId > 0) {
      post {
        rootView.findViewById<View>(emptyViewId)?.let { view ->
          emptyView = view
          showCorrectView()
        }
      }
    }
  }

  /**
   * Decides which view should be visible (recycler view or empty view) and shows it
   * To do that, it checks for the presence of [.isInEmptyState] callback and uses it to determine whether or not
   * the empty view should be shown.
   * If the callback was not set, then it uses the adapter item count information, where zero elements means the empty
   * state should be shown.
   */
  private fun showCorrectView() {
    val adapter = adapter
    emptyView?.let {
      val hasItems = isInEmptyState?.let { it(this) } ?: (adapter == null || adapter.itemCount > 0)
      it.visibility = if (hasItems) View.GONE else View.VISIBLE
      visibility = if (hasItems) View.VISIBLE else View.GONE
    }
  }

  override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
    super.setAdapter(adapter)
    adapter?.let {
      try {
        adapter.registerAdapterDataObserver(emptyObserver)
      } catch (ignored: IllegalStateException) { // the observer is already registered
      }
    }
    emptyObserver.onChanged()
  }

  /**
   * Sets the empty view.
   * If null, the [.showCorrectView] method will yield no further effect, unless the [.emptyViewId] was
   * set and the [.resetState] is called.
   *
   * @param emptyView the empty view to show on empty state
   */
  fun setEmptyView(emptyView: View?) {
    this.emptyView = emptyView
  }

  /**
   * Sets the empty state callback check.
   * The callback will be called each time a decision is to be made to whether show or hide the empty view.
   *
   * @param isInEmptyState the callback function to determine if the recycler view is in an empty state
   */
  fun setIsInEmptyState(isInEmptyState: ((RecyclerView) -> Boolean)?) {
    this.isInEmptyState = isInEmptyState
    showCorrectView()
  }

  /**
   * Resets the state of the recycler view.
   * If no empty view is present, an attempt to retrieve it from the resource id will be made.
   * If it's already present, then the recycler view will check whether or not is in an empty state and act
   * accordingly (show/hide the empty view/itself).
   */
  fun resetState() {
    if (emptyView == null) {
      initializeEmptyView()
    } else {
      showCorrectView()
    }
  }
}
