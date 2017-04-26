package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface RecycleItemType<I, VH extends RecyclerView.ViewHolder> {
  @NonNull
  VH onCreateViewHolder(ViewGroup parent);
  void getOnBindViewHolder(VH viewHolder, I item, int position);
}
