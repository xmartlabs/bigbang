package com.xmartlabs.template.ui.common

import android.support.annotation.Dimension
import android.support.annotation.Px
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.RecyclerView
import com.xmartlabs.bigbang.core.extensions.dpToPx

/** An OnDemandLoadingScrollListener for recycler view pagination */
abstract class OnDemandRecyclerViewScrollListener(private val recyclerView: RecyclerView) : NestedScrollView.OnScrollChangeListener {
  companion object {
    @Px
    private val DEFAULT_VISIBLE_THRESHOLD_DP = 100.dpToPx
  }
  
  var enabled = true
  @Dimension(unit = Dimension.PX)
  var visibleThreshold = 0
  
  private var previousRecyclerViewHeight = 0
  private var loading = true
  private var page = 1

  init {
    visibleThreshold = DEFAULT_VISIBLE_THRESHOLD_DP
    @Suppress("LeakingThis")
    loadNextPage(page)
  }

  /** Resets to the initial nested scroll view values */
  fun resetStatus() {
    enabled = true
    visibleThreshold = DEFAULT_VISIBLE_THRESHOLD_DP
    loading = false
    previousRecyclerViewHeight = recyclerView.measuredHeight
    page = 1
  }

  /**
   * Called when the scroll position of the nested scroll view changes.
   * @param nestedScrollView The view whose scroll position has changed.
   * *
   * @param scrollX          Current horizontal scroll origin.
   * *
   * @param scrollY          Current vertical scroll origin.
   * *
   * @param oldScrollX       Previous horizontal scroll origin.
   * *
   * @param oldScrollY       Previous vertical scroll origin.
   */
  override fun onScrollChange(nestedScrollView: NestedScrollView, scrollX: Int, scrollY: Int,
                              oldScrollX: Int, oldScrollY: Int) {
    if (previousRecyclerViewHeight < recyclerView.measuredHeight) {
      loading = false
      page++
      previousRecyclerViewHeight = recyclerView.measuredHeight
    }

    if (scrollY + visibleThreshold >= recyclerView.measuredHeight - nestedScrollView.measuredHeight &&
        scrollY > oldScrollY && !loading && enabled) {
      loading = true
      loadNextPage(page)
    }
  }

  /**
   * Called when the scroll position of the nested scroll view reaches the end of the current page.
   * @param page The next page to be loaded.
   */
  protected abstract fun loadNextPage(page: Int)
}
