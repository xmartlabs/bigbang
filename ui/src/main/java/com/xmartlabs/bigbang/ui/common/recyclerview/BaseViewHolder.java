package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder {
  public BaseViewHolder(@NonNull View view) {
    super(view);
    ButterKnife.bind(this, view);
  }

  /** @return a Resources instance for the application's package */
  protected final Resources getResources() {
    return getContext().getResources();
  }

  /**
   * Returns the context the view is running in, through which it can
   * access the current theme, resources, etc.
   *
   * @return The view's Context.
   */
  protected final Context getContext() {
    return itemView.getContext();
  }
}
