package com.scottruth.timeoffandroid.ui.demo;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scottruth.timeoffandroid.R;
import com.scottruth.timeoffandroid.model.demo.DemoRepo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by remer on 10/12/2015.
 */
public class DemoAdapter extends BaseAdapter {
    private final List<DemoRepo> items;

    public DemoAdapter() {
        items = new ArrayList<>();
    }

    public List<DemoRepo> getItems() {
        return items;
    }

    public void setItems(List<DemoRepo> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public DemoRepo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DemoRepo repo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo_repo, parent, false);
        }

        RepoItemViewHolder repoViewHolder = new RepoItemViewHolder(convertView);
        repoViewHolder.nameTextView.setText(repo.getName());

        return convertView;
    }

    static class RepoItemViewHolder {
        @Bind(R.id.name_textView)
        TextView nameTextView;
//        @Bind(R.id.text2)
//        TextView indicator;
//
        RepoItemViewHolder(View view) {
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
