package com.xmartlabs.bigbang.ui.recyclerview.multipleitems;

import com.xmartlabs.bigbang.ui.recyclerview.common.Brand;
import com.xmartlabs.bigbang.ui.recyclerview.common.ListActivity;

import java.util.List;

public class MultipleItemActivity extends ListActivity<BrandCarAdapter> {
  @Override
  public BrandCarAdapter createAdapter() {
    return new BrandCarAdapter();
  }

  public void setItems(List<Brand> brands) {
    adapter.setItems(brands);
  }
}
