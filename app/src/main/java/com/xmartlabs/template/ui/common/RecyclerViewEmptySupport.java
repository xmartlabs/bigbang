package com.xmartlabs.template.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.xmartlabs.template.R;

/**
 * Created by mirland on 10/03/17.
 */
public class RecyclerViewEmptySupport extends RecyclerView {
  @Nullable
  private View emptyView;
  @IdRes
  private int emptyViewID;

  private final RecyclerView.AdapterDataObserver emptyObserver = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      super.onChanged();
      checkViewVisibility();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      super.onItemRangeInserted(positionStart, itemCount);
      checkViewVisibility();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      super.onItemRangeRemoved(positionStart, itemCount);
      checkViewVisibility();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
      super.onItemRangeChanged(positionStart, itemCount);
      checkViewVisibility();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
      super.onItemRangeMoved(fromPosition, toPosition, itemCount);
      checkViewVisibility();
    }
  };

  public RecyclerViewEmptySupport(Context context) {
    this(context, null);
  }

  public RecyclerViewEmptySupport(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RecyclerViewEmptySupport(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    bindAttributes(context, attrs);
    initializeEmptyView();
  }

  private void bindAttributes(Context context, AttributeSet attrs) {
    TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.Commons, 0, 0);
    emptyViewID = attributes.getResourceId(R.styleable.Commons_emptyView, -1);
    attributes.recycle();
  }

  private void initializeEmptyView() {
    if (emptyViewID != -1) {
      post(() -> {
        View view = getRootView().findViewById(emptyViewID);
        if (view != null) {
          emptyView = view;
          checkViewVisibility();
        }
      });
    }
  }

  private void checkViewVisibility() {
    Adapter<?> adapter = getAdapter();
    if (adapter != null && emptyView != null) {
      if (adapter.getItemCount() == 0) {
        emptyView.setVisibility(View.VISIBLE);
        setVisibility(View.GONE);
      } else {
        emptyView.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
      }
    }
  }

  @Override
  public void setAdapter(Adapter adapter) {
    super.setAdapter(adapter);
    if (adapter != null) {
      try {
        adapter.registerAdapterDataObserver(emptyObserver);
      } catch (IllegalStateException ignored) { // the observer is already registered
      }
    }
    emptyObserver.onChanged();
  }

  public void setEmptyView(@Nullable View emptyView) {
    this.emptyView = emptyView;
  }
}
