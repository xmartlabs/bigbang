package com.xmartlabs.bigbang.ui.common.recyclerview;

/**
 * Created by mirland on 20/04/2017.
 */
public abstract class SimpleItemRecycleItemType<I, VH extends SingleItemBaseViewHolder<I>>
    implements RecycleItemType<I, VH> {
  public void getOnBindViewHolder(VH viewHolder, I item, int position) {
    viewHolder.bindItem(item);
  }
}
