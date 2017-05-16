package com.xmartlabs.template.ui.recyclerfragmentexample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.xmartlabs.template.R;
import com.xmartlabs.template.ui.common.BaseRecyclerViewAdapter;
import com.xmartlabs.template.ui.common.BaseViewHolder;

import java.util.List;

public class RecyclerExampleAdapter
    extends BaseRecyclerViewAdapter<String, RecyclerExampleAdapter.RecyclerExampleViewHolder> {
  public RecyclerExampleAdapter(List<String> strings) {
    setItems(strings);
  }

  @Override
  public RecyclerExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflateView(parent, R.layout.item_recycler_example);
    return new RecyclerExampleViewHolder(view);
  }

  @Override
  protected void onBindItemViewHolder(@NonNull RecyclerExampleViewHolder holder, @Nullable String item) {
    Optional.ofNullable(item)
        .ifPresent(holder::bind);
  }

  static class RecyclerExampleViewHolder extends BaseViewHolder {
    RecyclerExampleViewHolder(@NonNull View view) {
      super(view);
    }

    void bind(@NonNull String string) {
      ((TextView) itemView).setText(string);
    }
  }
}
