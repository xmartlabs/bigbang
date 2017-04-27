package com.xmartlabs.bigbang.ui.recyclerview.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xmartlabs.bigbang.ui.test.R;

public abstract class ListActivity<T extends RecyclerView.Adapter> extends Activity {
  RecyclerView recyclerView;

  protected final T adapter = createAdapter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_with_list);
    setupRecyclerViewAdapter();
  }

  private void setupRecyclerViewAdapter() {
    recyclerView = (RecyclerView) findViewById(com.xmartlabs.bigbang.ui.test.R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }

  public abstract T createAdapter();
}
