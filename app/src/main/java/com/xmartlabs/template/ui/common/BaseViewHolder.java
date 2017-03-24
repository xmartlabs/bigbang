package com.xmartlabs.template.ui.common;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

/**
 * Created by mike on 20/03/2017.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
  public BaseViewHolder(@NonNull View view) {
    super(view);
    ButterKnife.bind(this, view);
  }

  protected final Resources getResources() {
    return getContext().getResources();
  }

  protected final Context getContext() {
    return itemView.getContext();
  }
}
