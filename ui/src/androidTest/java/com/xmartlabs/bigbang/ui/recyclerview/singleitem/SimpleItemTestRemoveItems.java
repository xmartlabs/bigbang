package com.xmartlabs.bigbang.ui.recyclerview.singleitem;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.ui.recyclerview.common.Car;

import org.junit.Test;

import java.util.List;

public class SimpleItemTestRemoveItems extends SimpleItemRecyclerViewTest {
  @Test
  public void testRemoveFirstItem() {
    List<Car> cars = getCarList();

    SingleItemActivity activity = activityRule.getActivity();
    CarAdapter activityCarAdapter = activity.getAdapter();
    activity.runOnUiThread(() -> activityCarAdapter.setItems(cars));

    checkItems(cars);
    Car first = cars.remove(0);
    activity.runOnUiThread(() -> activityCarAdapter.removeItem(first));
    checkItems(cars);
  }

  @Test
  public void testRemoveMiddleItem() {
    List<Car> cars = getCarList();

    SingleItemActivity activity = activityRule.getActivity();
    CarAdapter activityCarAdapter = activity.getAdapter();
    activity.runOnUiThread(() -> activityCarAdapter.setItems(cars));

    checkItems(cars);
    Car car = cars.remove(cars.size() / 2);
    activity.runOnUiThread(() -> activityCarAdapter.removeItem(car));
    checkItems(cars);
  }

  @Test
  public void testRemoveLastItem() {
    List<Car> cars = getCarList();

    SingleItemActivity activity = activityRule.getActivity();
    CarAdapter activityCarAdapter = activity.getAdapter();
    activity.runOnUiThread(() -> activityCarAdapter.setItems(cars));

    checkItems(cars);
    Car car = cars.remove(cars.size() - 1);
    activity.runOnUiThread(() -> activityCarAdapter.removeItem(car));
    checkItems(cars);
  }

  @Test
  public void testRemoveOneItemWithMultipleInstance() {
    Car carToRemove = new Car("New Car");
    List<Car> cars = getCarList();
    cars.add(carToRemove);
    cars.add(2, carToRemove);
    cars.add(5, carToRemove);

    SingleItemActivity activity = activityRule.getActivity();
    CarAdapter activityCarAdapter = activity.getAdapter();
    activity.runOnUiThread(() -> activityCarAdapter.setItems(cars));
    checkItems(cars);

    activity.runOnUiThread(() -> activityCarAdapter.removeItem(carToRemove));
    List<Car> newCards = Stream.of(cars)
        .filterNot(car -> Objects.equals(car, carToRemove))
        .toList();
    checkItems(newCards);
  }
}
