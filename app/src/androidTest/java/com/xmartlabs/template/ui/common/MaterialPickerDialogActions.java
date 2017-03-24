package com.xmartlabs.template.ui.common;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerController;
import com.wdullaer.materialdatetimepicker.date.DayPickerView;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import org.hamcrest.Matcher;

import java.lang.reflect.Field;

import timber.log.Timber;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

/**
 * Copied from http://stackoverflow.com/a/34673382/1165181.
 */
public class MaterialPickerDialogActions {
  public static ViewAction setDate(final int year, final int monthOfYear, final int dayOfMonth) {
    return new ViewAction() {
      @Override
      public void perform(UiController uiController, View view) {
        final DayPickerView dayPickerView = (DayPickerView) view;

        try {
          Field field = DayPickerView.class.getDeclaredField("mController");
          field.setAccessible(true);
          DatePickerController controller = (DatePickerController) field.get(dayPickerView);
          controller.onDayOfMonthSelected(year, monthOfYear, dayOfMonth);
        } catch (Exception e) {
          Timber.e(e);
        }
      }

      @Override
      public String getDescription() {
        return "set date";
      }

      @Override
      public Matcher<View> getConstraints() {
        return allOf(isAssignableFrom(DayPickerView.class), isDisplayed());
      }
    };
  }

  public static ViewAction setTime(final int hours, final int minutes) {
    return new ViewAction() {
      @Override
      public void perform(UiController uiController, View view) {
        final RadialPickerLayout timePicker = (RadialPickerLayout) view;

        timePicker.setTime(new Timepoint(hours, minutes, 0));
      }

      @Override
      public String getDescription() {
        return "set time";
      }

      @Override
      public Matcher<View> getConstraints() {
        return allOf(isAssignableFrom(RadialPickerLayout.class), isDisplayed());
      }
    };
  }
}
