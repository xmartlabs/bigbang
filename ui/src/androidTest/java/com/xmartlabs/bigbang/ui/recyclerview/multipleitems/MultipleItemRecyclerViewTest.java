package com.xmartlabs.bigbang.ui.recyclerview.multipleitems;

import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.ui.recyclerview.common.Brand;
import com.xmartlabs.bigbang.ui.recyclerview.common.Car;
import com.xmartlabs.bigbang.ui.recyclerview.common.RecyclerViewAssertions;
import com.xmartlabs.bigbang.ui.test.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MultipleItemRecyclerViewTest {
  @Rule
  public ActivityTestRule<MultipleItemActivity> mActivityRule = new ActivityTestRule<>(MultipleItemActivity.class);

  @Test
  public void testSetItems() {
    List<Brand> brands = getBrands();

    MultipleItemActivity activity = mActivityRule.getActivity();
    activity.runOnUiThread(() -> activity.setItems(brands));

    checkRecyclerViewCondition(RecyclerViewAssertions.countIs(6));
    Stream.of(brands)
        .flatMap(brand -> Stream.concat(
            Stream.of(brand.getName()),
            Stream.ofNullable(brand.getCars()).map(Car::getModel)))
        .indexed()
        .forEach(carIntPair -> checkRecyclerViewCondition(
            RecyclerViewAssertions.atPosition(carIntPair.getFirst(), withText(carIntPair.getSecond()))));
  }

  private void checkRecyclerViewCondition(Matcher<View> viewMatcher) {
    ViewInteraction recyclerViewInteraction = onView(withId(R.id.recycler_view));
    recyclerViewInteraction.check(matches(viewMatcher));
  }

  @NonNull
  private List<Brand> getBrands() {
    Car corsa = new Car("Corsa");
    Car gol = new Car("Gol");
    Car golf = new Car("Golf");
    Car saveiro = new Car("Saveiro");
    Brand chevrolet = Brand.builder()
        .name("Chevrolet")
        .cars(Collections.singletonList(corsa))
        .build();
    Brand volkswagen = Brand.builder()
        .name("Volkswagen")
        .cars(Arrays.asList(gol, golf, saveiro))
        .build();
    return Arrays.asList(chevrolet, volkswagen);
  }
}
