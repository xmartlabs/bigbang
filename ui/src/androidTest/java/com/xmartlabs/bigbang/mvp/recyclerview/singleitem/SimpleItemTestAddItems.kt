package com.xmartlabs.bigbang.mvp.recyclerview.singleitem

import com.xmartlabs.bigbang.mvp.recyclerview.common.Car
import org.junit.Test
import java.util.Arrays

class SimpleItemTestAddItems : SimpleItemRecyclerViewTest() {
  private fun testAddItem(initialItems: List<Car>, itemsToAdd: List<Car>) {
    val activity = activityRule.activity
    val activityCarAdapter = activity.adapter
    activity.runOnUiThread { activityCarAdapter.setItems(initialItems) }

    checkItems(initialItems)
    activity.runOnUiThread { activityCarAdapter.addItems(itemsToAdd) }

    val resultList = initialItems.toMutableList().apply { addAll(itemsToAdd) }
    checkItems(resultList)
  }

  @Test
  fun testAddOneItem() {
    testAddItem(carList, listOf(Car("New car")))
  }

  @Test
  fun testAddOneItemToAnEmptyList() {
    testAddItem(emptyList(), listOf(Car("New car")))
  }

  @Test
  fun testAddMultipleItem() {
    val cars = carList
    val car1 = Car("New Car")
    val car2 = Car("New Car2")
    val carList = Arrays.asList(car1, car2)
    testAddItem(cars, carList)
  }

  @Test
  fun testAddOneItemTwice() {
    val cars = carList
    val car1 = Car("New Car")
    val carList = Arrays.asList(car1, car1)
    testAddItem(cars, carList)
  }
}
