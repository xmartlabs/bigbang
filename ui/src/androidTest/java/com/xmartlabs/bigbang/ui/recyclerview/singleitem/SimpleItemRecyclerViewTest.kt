package com.xmartlabs.bigbang.ui.recyclerview.singleitem

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.xmartlabs.bigbang.test.extensions.checkRecyclerViewAtPosition
import com.xmartlabs.bigbang.test.extensions.checkRecyclerViewCountIs
import com.xmartlabs.bigbang.ui.recyclerview.common.Car
import com.xmartlabs.bigbang.ui.test.R
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class SimpleItemRecyclerViewTest {
  @Rule
  @JvmField var activityRule = ActivityTestRule(SingleItemActivity::class.java)
  internal val carList: MutableList<Car> = listOf("Corsa", "Gol", "Golf", "Saveiro", "Partner")
      .map(::Car)
      .toMutableList()

  internal fun checkItems(cars: Collection<Car>) {
    val recyclerViewInteraction = onView(withId(R.id.recycler_view))
    recyclerViewInteraction.checkRecyclerViewCountIs(cars.size)

    cars
        .map(Car::model)
        .forEachIndexed { index, model -> recyclerViewInteraction.checkRecyclerViewAtPosition(index, withText(model)) }
  }
}
