package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;

import com.xmartlabs.bigbang.core.helper.ui.MetricsHelper;

import lombok.Setter;

/**
 * An OnDemandLoadingScrollListener for recycler view pagination
 */
public abstract class OnDemandRecyclerViewScrollListener implements NestedScrollView.OnScrollChangeListener {
  private static final int DEFAULT_VISIBLE_THRESHOLD_DP = 100;
  private final RecyclerView recyclerView;

  private int previousRecyclerViewHeight;
  private boolean loading = true;
  private int page = 1;

  @Setter
  private boolean enabled = true;
  @Setter
  @Dimension(unit = Dimension.PX)
  private int visibleThreshold;

  public OnDemandRecyclerViewScrollListener(@NonNull RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
    visibleThreshold = MetricsHelper.dpToPxInt(recyclerView.getResources(), DEFAULT_VISIBLE_THRESHOLD_DP);
    loadNextPage(page);
  }

  /**
   * Resets to the initial nested scroll view values
   */
  public void resetStatus() {
    enabled = true;
    visibleThreshold = MetricsHelper.dpToPxInt(recyclerView.getResources(), DEFAULT_VISIBLE_THRESHOLD_DP);
    loading = false;
    previousRecyclerViewHeight = recyclerView.getMeasuredHeight();
    page = 1;
  }

  /**
   * Called when the scroll position of the nested scroll view changes.
   *
   * @param nestedScrollView The view whose scroll position has changed.
   * @param scrollX          Current horizontal scroll origin.
   * @param scrollY          Current vertical scroll origin.
   * @param oldScrollX       Previous horizontal scroll origin.
   * @param oldScrollY       Previous vertical scroll origin.
   */
  @Override
  public void onScrollChange(@NonNull NestedScrollView nestedScrollView, int scrollX, int scrollY,
                             int oldScrollX, int oldScrollY) {
    if (previousRecyclerViewHeight < recyclerView.getMeasuredHeight()) {
      loading = false;
      page++;
      previousRecyclerViewHeight = recyclerView.getMeasuredHeight();
    }

    if ((scrollY + visibleThreshold >= (recyclerView.getMeasuredHeight() - nestedScrollView.getMeasuredHeight())) &&
        scrollY > oldScrollY && !loading && enabled) {
      loading = true;
      loadNextPage(page);
    }
  }

  /**
   * Called when the scroll position of the nested scroll view reaches the end of the current page.
   *
   * @param page The next page to be loaded.
   */
  protected abstract void loadNextPage(int page);
}
