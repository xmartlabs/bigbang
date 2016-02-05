package com.xmartlabs.template.ui;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Objects;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by santiago on 01/10/15.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerItemViewHolder> {
  @NonNull
  private final List<DrawerItem> items;
  @NonNull
  private final OnItemClickListener onItemClickListener;
  @Nullable
  private Integer selectedPosition;

  public interface OnItemClickListener {
    void onItemClick(@NonNull DrawerItem item);
  }

  public DrawerAdapter(@NonNull OnItemClickListener onItemClickListener) {
    items = Arrays.asList(DrawerItem.values());
    this.onItemClickListener = onItemClickListener;
  }

  @Override
  @NonNull
  public DrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

    DrawerItemType drawerItemType = DrawerItemType.fromInteger(viewType);

    @LayoutRes
    int layoutResId = drawerItemType.getLayoutResId();

    View view = layoutInflater.inflate(layoutResId, parent, false);

    switch (drawerItemType) {
      case ABOVE:
        return new DrawerAboveItemViewHolder(view);
      case DIVIDER:
        return new DrawerDividerItemViewHolder(view);
      case BELOW:
        return new DrawerBelowItemViewHolder(view);
      default:
        throw new IllegalArgumentException("Invalid drawer item type value");
    }
  }

  @Override
  public void onBindViewHolder(DrawerItemViewHolder holder, int position) {
    final DrawerItem item = getItem(position);
    DrawerItemType drawerItemType = item.getDrawerItemType();
    switch (drawerItemType) {
      case ABOVE:
        DrawerAboveItemViewHolder aboveHolder = (DrawerAboveItemViewHolder) holder;

        Integer drawableResId = item.getDrawableResId();
        if (drawableResId != null) {
          aboveHolder.imageView.setImageResource(drawableResId);
        }
        // fall through
      case BELOW:
        DrawerBelowItemViewHolder belowHolder = (DrawerBelowItemViewHolder) holder;

        belowHolder.textView.setText(item.toString());
        belowHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(item));
        belowHolder.itemView.setSelected(Objects.equals(position, selectedPosition));
        break;
      default:
    }
  }

  @NonNull
  public DrawerItem getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    DrawerItem drawerItem = getItem(position);
    return drawerItem.getValue();
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  @Override
  public int getItemViewType(int position) {
    DrawerItem drawerItem = getItem(position);
    DrawerItemType drawerItemType = drawerItem.getDrawerItemType();
    return drawerItemType.getValue();
  }

  public int getItemPosition(@NonNull DrawerItem drawerItem) {
    return drawerItem.getValue();
  }

  public void selectItemIfSelectable(@NonNull DrawerItem item) {
    if (item.isSelectable()) {
      if (selectedPosition != null) {
        notifyItemChanged(selectedPosition);
      }
      selectedPosition = getItemPosition(item);
      notifyItemChanged(selectedPosition);
    }
  }

  @Nullable
  public DrawerItem getSelected() {
    return selectedPosition == null ? null : getItem(selectedPosition);
  }

  public boolean isSelected(@NonNull DrawerItem item) {
    DrawerItem selectedItem = getSelected();
    return Objects.equals(item, selectedItem);
  }

  static class DrawerItemViewHolder extends RecyclerView.ViewHolder {
    DrawerItemViewHolder(@NonNull View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  static class DrawerAboveItemViewHolder extends DrawerBelowItemViewHolder {
    @Bind(android.R.id.icon)
    ImageView imageView;

    DrawerAboveItemViewHolder(@NonNull View view) {
      super(view);
    }
  }

  static class DrawerDividerItemViewHolder extends DrawerItemViewHolder {
    DrawerDividerItemViewHolder(@NonNull View view) {
      super(view);
    }
  }

  static class DrawerBelowItemViewHolder extends DrawerItemViewHolder {
    @Bind(android.R.id.text1)
    TextView textView;

    DrawerBelowItemViewHolder(@NonNull View view) {
      super(view);
    }
  }
}
