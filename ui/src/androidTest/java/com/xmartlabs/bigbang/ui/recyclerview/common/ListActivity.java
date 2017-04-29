package com.xmartlabs.bigbang.ui.recyclerview.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xmartlabs.bigbang.ui.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

public abstract class ListActivity<T extends RecyclerView.Adapter> extends Activity {
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  @Getter
  private final T adapter = createAdapter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_with_list);
    ButterKnife.bind(this);
    setupRecyclerViewAdapter();
  }

  private void setupRecyclerViewAdapter() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }

  public abstract T createAdapter();
}
