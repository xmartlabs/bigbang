package com.xmartlabs.bigbang.ui.recyclerview.singleitem

import com.xmartlabs.bigbang.ui.recyclerview.common.Car
import org.junit.Test
import java.util.Objects
import java.util.Arrays
import java.util.Collections

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
    activity.runOnUiThread { activity.adapter.setItems(updatedCars) }

    checkItems(updatedCars)
  }

  @Test
  fun testUpdateItemsWithMoreItems() {
    val cars = carList

    val activity = activityRule.activity
    activity.runOnUiThread { activity.adapter.setItems(cars) }

    checkItems(cars)

    cars.addAll(cars)
    activity.runOnUiThread { activity.adapter.setItems(cars, { car1, car2 -> car1 == car2 }, Objects::equals) }

    checkItems(cars)
  }

  @Test
  fun testUpdateItemsWithTheSameAmountOfItems() {
    val cars = carList

    val activity = activityRule.activity
    activity.runOnUiThread { activity.adapter.setItems(cars) }

    checkItems(cars)
    activity.runOnUiThread { activity.adapter.setItems(cars, { car1, car2 -> car1 == car2 }, Objects::equals) }
    checkItems(cars)
  }

  @Test
  fun testUpdateItemsWithDifferentItems() {
    val activity = activityRule.activity

    activity.runOnUiThread { activity.adapter.setItems(carList, { car1, car2 -> car1 == car2 }, Objects::equals) }
    checkItems(carList)

    val otherCars = Arrays.asList(Car("VW Gol"), Car("VW Up!"))
    activity.runOnUiThread { activity.adapter.setItems(otherCars, { car1, car2 -> car1 == car2 }, Objects::equals) }
    checkItems(otherCars)
  }

  @Test
  fun testUpdateItemsWithNoItems() {
    var cars = Collections.emptyList<Car>()

    val activity = activityRule.activity

    activity.runOnUiThread { activity.adapter.setItems(cars, { car1, car2 -> car1 == car2 }, Objects::equals) }
    checkItems(cars)

    cars = carList
    activity.runOnUiThread { activity.adapter.setItems(cars, { car1, car2 -> car1 == car2 }, Objects::equals) }
    checkItems(cars)

    cars = Collections.emptyList()
    activity.runOnUiThread { activity.adapter.setItems(cars, { car1, car2 -> car1 == car2 }, Objects::equals) }
    checkItems(cars)
  }
}
