package com.xmartlabs.template.ui.common;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RequestsVerifier;
import io.appflate.restmock.utils.RequestMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.xmartlabs.template.ui.common.RecyclerViewAssertions.countIs;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

/**
 * Created by medina on 21/09/2016.
 */
@RunWith(AndroidJUnit4.class)
public abstract class SingleActivityWithRecyclerViewInstrumentationTest<T extends Activity>
    extends SingleActivityInstrumentationTest<T> {
  protected void checkListSizeTest(@NonNull RecyclerViewItemsTestData testData) {
    RequestMatcher requestMatcher = null;
    if (testData.getServiceUrl() != null) {
      requestMatcher = pathContains("/" + testData.getServiceUrl());
      RESTMockServer
          .whenGET(requestMatcher)
          .thenReturnFile(testData.getJsonResponsePath());
    }

    getActivityTestRule().launchActivity(testData.getIntent());

    Stream.of(testData.getContent())
        .forEach(item ->
            onView(allOf(isAssignableFrom(RecyclerView.class),
                anyOf(withId(item.first), isDescendantOfA(withId(item.first)))))
                .check(matches(countIs(item.second)))
        );

    if (requestMatcher != null) {
      RequestsVerifier.verifyGET(requestMatcher).invoked();
    }
  }

  @Test
  public void hasNoItemsRecyclerView() throws Throwable {
    checkListSizeTest(getNoItemsRecyclerViewItemsTestData());
  }

  @Test
  public void hasItemCountRecyclerView() {
    checkListSizeTest(getItemsRecyclerViewItemsTestData());
  }

  public abstract RecyclerViewItemsTestData getNoItemsRecyclerViewItemsTestData();

  public abstract RecyclerViewItemsTestData getItemsRecyclerViewItemsTestData();
}
