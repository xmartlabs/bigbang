package com.xmartlabs.bigbang.ui.recyclerview.singleitem;

import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.ui.recyclerview.common.Car;
import com.xmartlabs.bigbang.ui.recyclerview.common.RecyclerViewAssertions;
import com.xmartlabs.bigbang.ui.test.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public abstract class SimpleItemRecyclerViewTest {
  @Rule
  public ActivityTestRule<SingleItemActivity> mActivityRule = new ActivityTestRule<>(SingleItemActivity.class);

  void checkItems(@NonNull List<Car> cars) {
    checkRecyclerViewCondition(RecyclerViewAssertions.countIs(cars.size()));
    Stream.of(cars)
        .map(Car::getModel)
        .indexed()
        .forEach(carIntPair -> checkRecyclerViewCondition(
            RecyclerViewAssertions.atPosition(carIntPair.getFirst(), withText(carIntPair.getSecond()))));
  }

  private void checkRecyclerViewCondition(Matcher<View> viewMatcher) {
    ViewInteraction recyclerViewInteraction = onView(withId(R.id.recycler_view));
    recyclerViewInteraction.check(matches(viewMatcher));
  }

  @NonNull
  List<Car> getCarList() {
    return Stream.of("Corsa", "Gol", "Golf", "Saveiro", "Partner")
        .map(Car::new)
        .toList();
  }
}
