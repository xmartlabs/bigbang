package com.xmartlabs.bigbang.ui.recyclerview.singleitem

import com.xmartlabs.bigbang.ui.recyclerview.common.Car
import org.junit.Test

/**
 * Created by diegomedina24 on 22/9/17.
 */
class SimpleItemTestUpdateItems : SimpleItemRecyclerViewTest() {
  @Test
  fun testUpdateItemsWithLessItems() {
    val cars = carList
  
    val activity = activityRule.activity
    activity.runOnUiThread { activity.adapter.setItems(cars) }
  
    checkItems(cars)
  
    val updatedCars = cars.subList(0, 2)
    activity.runOnUiThread { activity.adapter.updateItems(updatedCars) }
  
    checkItems(updatedCars)
  }
  
  @Test
  fun testUpdateItemsWithMoreItems() {
    val cars = carList
  
    val activity = activityRule.activity
    activity.runOnUiThread { activity.adapter.setItems(cars) }
  
    checkItems(cars)
  
    cars.addAll(cars)
    activity.runOnUiThread { activity.adapter.updateItems(cars) }
  
    checkItems(cars)
  }
  
  @Test
  fun testUpdateItemsWithTheSameAmountOfItems() {
    val cars = carList
  
    val activity = activityRule.activity
    activity.runOnUiThread { activity.adapter.setItems(cars) }
  
    checkItems(cars)
    activity.runOnUiThread { activity.adapter.updateItems(cars) }
    checkItems(cars)
  }
  
  @Test
  fun testUpdateItemsWithNoItems() {
    var cars = listOf<Car>()
  
    val activity = activityRule.activity
    
    activity.runOnUiThread { activity.adapter.updateItems(cars) }
    checkItems(cars)
    
    cars = carList
    activity.runOnUiThread { activity.adapter.updateItems(cars) }
    checkItems(cars)
    
    cars = listOf()
    activity.runOnUiThread { activity.adapter.updateItems(cars) }
    checkItems(cars)
  }
}
