package com.xmartlabs.bigbang.ui.recyclerview.singleitem;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.ui.recyclerview.common.Car;
import com.xmartlabs.bigbang.ui.recyclerview.common.RecyclerViewAssertions;
import com.xmartlabs.bigbang.ui.test.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SimpleItemRecyclerViewTest {
  @Rule
  public ActivityTestRule<SingleItemActivity> mActivityRule = new ActivityTestRule<>(SingleItemActivity.class);

  @Test
  public void tesSetItems() {
    Car car1 = new Car("Corsa");
    Car car2 = new Car("Gol");
    Car car3 = new Car("Golf");
    Car car4 = new Car("Saveiro");
    Car car5 = new Car("Partner");

    SingleItemActivity activity = mActivityRule.getActivity();
    List<Car> cars = Arrays.asList(car1, car2, car3, car4, car5);
    activity.runOnUiThread(() -> activity.setItems(cars));
    ViewInteraction recyclerViewInteraction = onView(withId(R.id.recycler_view));
    recyclerViewInteraction
        .check(matches(RecyclerViewAssertions.countIs(5)));
    Stream.of(cars)
        .indexed()
        .forEach(carIntPair ->
            recyclerViewInteraction.check(matches(
                RecyclerViewAssertions.atPosition(carIntPair.getFirst(), withText(carIntPair.getSecond().getModel())))));
  }
}
