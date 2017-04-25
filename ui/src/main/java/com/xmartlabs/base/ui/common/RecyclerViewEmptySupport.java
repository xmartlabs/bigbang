package com.xmartlabs.base.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Function;
import com.xmartlabs.base.ui.R;

/**
 * {@link RecyclerView} subclass that automatically handles empty state.
 *
 * A {@link RecyclerView} is in an empty state when its adapter holds zero items,
 * or if the callback function {@link #isInEmptyState} is set and returns true.
 *
 * In the empty state, the {@link } will be hidden and a view will be shown.
 * The empty state view to be shown can be defined in two ways:
 * <ol>
 *   <li>By means of {@link #setEmptyView(View)} method</li>
 *   <li>By setting the attribute {@code app:emptyViewId} in the recycler view and point to a view in the hierarchy</li>
 * </ol>
 */
public class RecyclerViewEmptySupport extends RecyclerView {
  @Nullable
  private View emptyView;
  @IdRes
  private int emptyViewId;
  @Nullable
  private Function<RecyclerView, Boolean> isInEmptyState;

  private final RecyclerView.AdapterDataObserver emptyObserver = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      super.onChanged();
      showCorrectView();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      super.onItemRangeInserted(positionStart, itemCount);
      showCorrectView();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      super.onItemRangeRemoved(positionStart, itemCount);
      showCorrectView();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
      super.onItemRangeChanged(positionStart, itemCount);
      showCorrectView();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
      super.onItemRangeMoved(fromPosition, toPosition, itemCount);
      showCorrectView();
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

  /**
   * Extracts the resource id from the {@link RecyclerView} attributes, if present.
   *
   * @param context The Context the view is running in, through which it can
   *        access the current theme, resources, etc.
   * @param attrs The attributes of the XML tag that is inflating the view.
   */
  private void bindAttributes(Context context, AttributeSet attrs) {
    TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.Commons, 0, 0);
    emptyViewId = attributes.getResourceId(R.styleable.Commons_emptyView, -1);
    attributes.recycle();
  }

  /** Initializes the empty view using the resource identifier {@link #emptyViewId}, if it exists. */
  private void initializeEmptyView() {
    if (emptyViewId > 0) {
      post(() -> Optional.ofNullable(getRootView().findViewById(emptyViewId))
          .ifPresent(view -> {
            emptyView = view;
            showCorrectView();
          }));
    }
  }

  /**
   * Decides which view should be visible (recycler view or empty view) and shows it
   *
   * To do that, it checks for the presence of {@link #isInEmptyState} callback and uses it to determine whether or not
   * the empty view should be shown.
   * If the callback was not set, then it uses the adapter item count information, where zero elements means the empty
   * state should be shown.
   */
  private void showCorrectView() {
    Adapter<?> adapter = getAdapter();
    if (emptyView != null) {
      boolean hasItems = Optional.ofNullable(isInEmptyState)
          .map(state -> state.apply(this))
          .orElse(adapter == null || adapter.getItemCount() > 0);
      emptyView.setVisibility(hasItems ? GONE : VISIBLE);
      setVisibility(hasItems ? VISIBLE : GONE);
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

  /**
   * Sets the empty view.
   *
   * If null, the {@link #showCorrectView()} method will yield no further effect, unless the {@link #emptyViewId} was
   * set and the {@link #resetState()} is called.
   *
   * @param emptyView the empty view to show on empty state
   */
  public void setEmptyView(@Nullable View emptyView) {
    this.emptyView = emptyView;
  }

  /**
   * Sets the empty state callback check.
   *
   * The callback will be called each time a decision is to be made to whether show or hide the empty view.
   *
   * @param isInEmptyState the callback function to determine if the recycler view is in an empty state
   */
  public void setIsInEmptyState(@Nullable Function<RecyclerView, Boolean> isInEmptyState) {
    this.isInEmptyState = isInEmptyState;
    showCorrectView();
  }

  /**
   * Resets the state of the recycler view.
   *
   * If no empty view is present, an attempt to retrieve it from the resource id will be made.
   * If it's already present, then the recycler view will check whether or not is in an empty state and act
   * accordingly (show/hide the empty view/itself).
   */
  public void resetState() {
    Optional.ofNullable(emptyView)
        .ifPresentOrElse(view -> showCorrectView(), this::initializeEmptyView);
  }
}
