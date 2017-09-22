package com.xmartlabs.bigbang.ui.recyclerview.singleitem

import com.xmartlabs.bigbang.ui.recyclerview.common.Car

import org.junit.Test

class SimpleItemTestRemoveItems : SimpleItemRecyclerViewTest() {
  @Test
  fun testRemoveFirstItem() {
    val cars = carList

    val activity = activityRule.activity
    val activityCarAdapter = activity.adapter
    activity.runOnUiThread { activityCarAdapter.setItems(cars) }

    checkItems(cars)
    val first = cars.removeAt(0)
    activity.runOnUiThread { activityCarAdapter.removeItem(first) }
    checkItems(cars)
  }

  @Test
  fun testRemoveMiddleItem() {
    val cars = carList

    val activity = activityRule.activity
    val activityCarAdapter = activity.adapter
    activity.runOnUiThread { activityCarAdapter.setItems(cars) }

    checkItems(cars)
    val car = cars.removeAt(cars.size / 2)
    activity.runOnUiThread { activityCarAdapter.removeItem(car) }
    checkItems(cars)
  }

  @Test
  fun testRemoveLastItem() {
    val cars = carList

    val activity = activityRule.activity
    val activityCarAdapter = activity.adapter
    activity.runOnUiThread { activityCarAdapter.setItems(cars) }

    checkItems(cars)
    val car = cars.removeAt(cars.size - 1)
    activity.runOnUiThread { activityCarAdapter.removeItem(car) }
    checkItems(cars)
  }

  @Test
  fun testRemoveOneItemWithMultipleInstance() {
    val carToRemove = Car("New Car")
    val cars = carList
    cars.add(carToRemove)
    cars.add(2, carToRemove)
    cars.add(5, carToRemove)

    val activity = activityRule.activity
    val activityCarAdapter = activity.adapter
    activity.runOnUiThread { activityCarAdapter.setItems(cars) }
    checkItems(cars)

    activity.runOnUiThread { activityCarAdapter.removeItem(carToRemove) }
    val newCards = cars.filterNot { car -> car == carToRemove }
    checkItems(newCards)
  }
  
  @Test
  fun testRemoveAtIndex() {
    val cars = carList
    
    val activity = activityRule.activity
    val activityCarAdapter = activity.adapter
    activity.runOnUiThread { activityCarAdapter.setItems(cars) }
    
    checkItems(cars)
    cars.removeAt(cars.size / 2)
    activity.runOnUiThread { activityCarAdapter.removeItemAtIndex(cars.size / 2) }
    checkItems(cars)
  }
}
