package com.xmartlabs.template.ui.common;

import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import com.xmartlabs.template.helper.ui.MetricsHelper;

import lombok.Setter;

/**
 * Created by mike on 22/03/2017.
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
  private int visibleThreshold = MetricsHelper.dpToPxInt(DEFAULT_VISIBLE_THRESHOLD_DP);

  public OnDemandRecyclerViewScrollListener(@NonNull RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
    loadNextPage(page);
  }

  public void resetStatus() {
    enabled = true;
    visibleThreshold = MetricsHelper.dpToPxInt(DEFAULT_VISIBLE_THRESHOLD_DP);
    loading = false;
    previousRecyclerViewHeight = recyclerView.getMeasuredHeight();
    page = 1;
  }

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

  protected abstract void loadNextPage(int page);
}
