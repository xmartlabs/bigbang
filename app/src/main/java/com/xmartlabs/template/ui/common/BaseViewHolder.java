package com.xmartlabs.template.ui.common;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * A Base View Holder
 * Setting, removing, adding, getting items count.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

  /**
   * View holder default constructor.
   */
  public BaseViewHolder(@NonNull View view) {
    super(view);
    ButterKnife.bind(this, view);
  }

  /**
   * Returns a Resources instance for the application's package.
   *
   * @return a Resources instance for the application's package
   */
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
