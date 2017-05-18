package com.xmartlabs.template.ui.recyclerfragmentexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xmartlabs.template.R;

import java.util.List;

class RecyclerExampleAdapter extends RecyclerView.Adapter<RecyclerExampleAdapter.RecyclerExampleViewHolder> {
  @NonNull
  private List<String> items;

  RecyclerExampleAdapter(@NonNull List<String> strings) {
    items = strings;
  }

  @Override
  public RecyclerExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_example, parent, false);
    return new RecyclerExampleViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerExampleViewHolder holder, int position) {
    holder.bind(items.get(position));
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  static class RecyclerExampleViewHolder extends RecyclerView.ViewHolder {
    RecyclerExampleViewHolder(@NonNull View view) {
      super(view);
    }

    void bind(@NonNull String string) {
      ((TextView) itemView).setText(string);
    }
  }
}
