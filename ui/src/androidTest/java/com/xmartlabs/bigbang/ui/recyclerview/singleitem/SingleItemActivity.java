package com.xmartlabs.bigbang.ui.recyclerview.singleitem;

import com.xmartlabs.bigbang.ui.recyclerview.common.ListActivity;

public class SingleItemActivity extends ListActivity<CarAdapter> {
  @Override
  public CarAdapter createAdapter() {
    return new CarAdapter();
  }
}
