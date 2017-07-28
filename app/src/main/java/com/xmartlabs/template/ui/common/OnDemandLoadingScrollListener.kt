package com.xmartlabs.template.ui.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * An OnDemandLoadingScrollListener for recycler view pagination
 */
abstract class OnDemandLoadingScrollListener : RecyclerView.OnScrollListener() {
  companion object {
    val VISIBLE_THRESHOLD_DEFAULT = 5
    val firstPage = 1
  }
  
  private var previousTotal: Int = 0
  private var loading = true
  private var firstVisibleItem: Int = 0
  private var visibleItemCount: Int = 0
  private var totalItemCount: Int = 0
  private var page = firstPage
  var enabled = true
  val visibleThreshold = VISIBLE_THRESHOLD_DEFAULT

  init {
    loadNextPage(page)
  }

  fun resetStatus(layoutManager: LinearLayoutManager) {
    enabled = true
    previousTotal = 0
    loading = false
    firstVisibleItem = 0
    visibleItemCount = getFirstVisibleItemPosition(layoutManager)
    totalItemCount = getTotalItemCount(layoutManager)
    page = firstPage
    checkIfLoadingIsNeeded()
  }

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)

    visibleItemCount = recyclerView.childCount
    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    totalItemCount = getTotalItemCount(layoutManager)
    firstVisibleItem = getFirstVisibleItemPosition(layoutManager)

    checkIfLoadingIsNeeded()
  }

  private fun checkIfLoadingIsNeeded() {
    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false
        page++
        previousTotal = totalItemCount
      }
    } else if (enabled && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
      loadNextPage(page)
      loading = true
    }
  }

  private fun getFirstVisibleItemPosition(layoutManager: LinearLayoutManager): Int {
    return layoutManager.findFirstVisibleItemPosition()
  }

  private fun getTotalItemCount(layoutManager: LinearLayoutManager): Int {
    return layoutManager.itemCount
  }

  protected abstract fun loadNextPage(page: Int)
}
