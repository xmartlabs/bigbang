package com.xmartlabs.bigbang.ui.recyclerview.singleitem;

import com.xmartlabs.bigbang.ui.recyclerview.common.Car;

import org.junit.Test;

import java.util.List;

/**
 * Created by mirland on 28/04/17.
 */
public class SimpleItemTestSetItems extends SimpleItemRecyclerViewTest {
  @Test
  public void testSetItems() {
    List<Car> cars = getCarList();

    SingleItemActivity activity = mActivityRule.getActivity();
    activity.runOnUiThread(() -> activity.getAdapter().setItems(cars));

    checkItems(cars);
  }
}
