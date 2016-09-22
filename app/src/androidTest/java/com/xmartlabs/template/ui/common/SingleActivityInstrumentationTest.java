package com.xmartlabs.template.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.Getter;
import timber.log.Timber;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by medina on 21/09/2016.
 */
@RunWith(AndroidJUnit4.class)
public abstract class SingleActivityInstrumentationTest<T extends Activity> extends BaseInstrumentationTest {
  protected static final String EMPTY_LIST_JSON_PATH = "jsons/list_empty.json";

  @Getter
  @Rule
  public ActivityTestRule<T> activityTestRule = createTestRule();

  protected abstract Class<T> getActivityClass();

  protected ActivityTestRule<T> createTestRule() {
    return new IntentsTestRule<>(getActivityClass(), true, false);
  }

  @Test
  public void checkActivityOpens() {
    launchActivityWithDefaultIntent();
  }

  @Test
  public void checkUpNavigation() {
    launchActivityWithDefaultIntent();
    ViewInteraction upButtonViewInteraction = getUpButtonViewInteraction();
    try {
      upButtonViewInteraction.perform(click());
    } catch (NoMatchingViewException e) {
      // Ignore if there is no up button.
    }
  }

  protected ViewInteraction getUpButtonViewInteraction() {
    return onView(allOf(isAssignableFrom(ImageButton.class), withParent(isAssignableFrom(Toolbar.class)),
        isClickable()));
  }

  protected void launchActivityWithDefaultIntent() {
    activityTestRule.launchActivity(getDefaultIntent());
  }

  @Nullable
  protected Intent getDefaultIntent() {
    return null;
  }

  protected void allowPermissionsIfNeeded() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      UiDevice device = UiDevice.getInstance(getInstrumentation());
      UiObject allowPermissions = device.findObject(new UiSelector().clickable(true).index(1));
      if (allowPermissions.exists()) {
        try {
          allowPermissions.click();
        } catch (UiObjectNotFoundException e) {
          Timber.e(e, "There is no permissions dialog to interact with ");
        }
      }
    }
  }
}
