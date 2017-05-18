package com.xmartlabs.bigbang.ui.recyclerview.multipleitems;

import android.support.annotation.NonNull;

import com.xmartlabs.bigbang.ui.recyclerview.common.Brand;
import com.xmartlabs.bigbang.ui.recyclerview.common.ListActivity;

import java.util.List;

public class MultipleItemActivity extends ListActivity<BrandCarAdapter> {
  @Override
  public BrandCarAdapter createAdapter() {
    return new BrandCarAdapter();
  }

  public void setItems(@NonNull List<Brand> brands) {
    getAdapter().setItems(brands);
  }
}
