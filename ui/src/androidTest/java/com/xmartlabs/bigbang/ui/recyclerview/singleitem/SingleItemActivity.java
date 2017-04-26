package com.xmartlabs.bigbang.ui.recyclerview.singleitem;

import com.xmartlabs.bigbang.ui.recyclerview.common.Car;
import com.xmartlabs.bigbang.ui.recyclerview.common.ListActivity;

import java.util.List;

public class SingleItemActivity extends ListActivity<CarAdapter> {
  @Override
  public CarAdapter createAdapter() {
    return new CarAdapter();
  }

  public void setItems(List<Car> cars) {
    adapter.setItems(cars);
  }
}
