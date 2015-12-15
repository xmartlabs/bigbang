package com.scottruth.timeoffandroid.ui;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scottruth.timeoffandroid.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by santiago on 01/10/15.
 */
public class DrawerAdapter extends BaseAdapter {
    private final List<DrawerItem> items;

    public DrawerAdapter() {
        items = Arrays.asList(DrawerItem.values());
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public DrawerItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getValue();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItem drawerItem = getItem(position);
        switch (drawerItem.getDrawerItemType()) {
            case MAIN:
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_above_drawer, parent, false);
                }
                DrawerAboveItemViewHolder drawerAboveItemViewHolder = new DrawerAboveItemViewHolder(convertView);
                Integer drawableResId = drawerItem.getDrawableResId();
                if (drawableResId != null) {
                    drawerAboveItemViewHolder.imageView.setImageResource(drawableResId);
                }
                drawerAboveItemViewHolder.textView.setText(drawerItem.toString());
                if (drawerItem.getNewMessagesCount() > 0) {
                    drawerAboveItemViewHolder.indicator.setVisibility(View.VISIBLE);
                    drawerAboveItemViewHolder.indicator.setText(String.valueOf(drawerItem.getNewMessagesCount()));
                } else {
                    drawerAboveItemViewHolder.indicator.setVisibility(View.GONE);
                }
                break;
            case DIVIDER:
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_divider_drawer, parent, false);
                }
                break;
            case SECONDARY:
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_below_drawer, parent, false);
                }
                DrawerBelowItemViewHolder drawerBelowItemViewHolder = new DrawerBelowItemViewHolder(convertView);
                drawerBelowItemViewHolder.textView.setText(drawerItem.toString());
                break;
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getDrawerItemType().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return DrawerItemType.values().length;
    }

    public int getItemPosition(@NonNull DrawerItem drawerItem) {
        return drawerItem.getValue();
    }

    static class DrawerAboveItemViewHolder {
        @Bind(android.R.id.icon)
        ImageView imageView;
        @Bind(android.R.id.text1)
        TextView textView;
        @Bind(android.R.id.text2)
        TextView indicator;

        DrawerAboveItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class DrawerBelowItemViewHolder {
        @Bind(android.R.id.text1)
        TextView textView;

        DrawerBelowItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
