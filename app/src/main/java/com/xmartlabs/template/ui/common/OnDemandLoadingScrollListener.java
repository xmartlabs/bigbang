package com.xmartlabs.template.ui.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import lombok.Setter;

/**
 * An OnDemandLoadingScrollListener for recycler view pagination
 */
public abstract class OnDemandLoadingScrollListener extends RecyclerView.OnScrollListener {
  private int previousTotal;
  private boolean loading = true;
  private int firstVisibleItem;
  private int visibleItemCount;
  private int totalItemCount;
  private int page = 1;

  @Setter
  private boolean enabled = true;
  @Setter
  private int visibleThreshold = 5;

  public OnDemandLoadingScrollListener() {
    loadNextPage(page);
  }

  public void resetStatus(@NonNull LinearLayoutManager layoutManager) {
    enabled = true;
    previousTotal = 0;
    loading = false;
    firstVisibleItem = 0;
    visibleItemCount = getFirstVisibleItemPosition(layoutManager);
    totalItemCount = getTotalItemCount(layoutManager);
    page = 1;
    checkIfLoadingIsNeeded();
  }

  @Override
  public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    visibleItemCount = recyclerView.getChildCount();
    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    totalItemCount = getTotalItemCount(layoutManager);
    firstVisibleItem = getFirstVisibleItemPosition(layoutManager);

    checkIfLoadingIsNeeded();
  }

  private void checkIfLoadingIsNeeded() {
    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false;
        page++;
        previousTotal = totalItemCount;
      }
    } else if (enabled && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
      loadNextPage(page);
      loading = true;
    }
  }

  private int getFirstVisibleItemPosition(@NonNull LinearLayoutManager layoutManager) {
    return layoutManager.findFirstVisibleItemPosition();
  }

  private int getTotalItemCount(@NonNull LinearLayoutManager layoutManager) {
    return layoutManager.getItemCount();
  }

  protected abstract void loadNextPage(int page);
}
