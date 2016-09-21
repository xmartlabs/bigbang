package com.xmartlabs.template.helper.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xmartlabs.template.helper.DateHelper;

import org.threeten.bp.Clock;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import lombok.Setter;

/**
 * Created by medina on 19/09/2016.
 */
public class DatePickerDialogHelper {
  @Setter
  OnDateSetListener listener;

  /**
   * Creates a <code>DatePickerDialog</code> instance
   * @param listener to be triggered when the user selects a date
   * @param clock to get the <code>Calendar</code> from
   * @return the <code>DatePickerDialog</code> created instance
   */
  @SuppressWarnings("unused")
  @NonNull
  public static DatePickerDialog createDialog(@Nullable OnDateSetListener listener, @NonNull Clock clock) {
    return createDialog(null, listener, clock);
  }

  /**
   * Creates a <code>DatePickerDialog</code> instance with the <code>date</code> selected
   * @param date the selected start date
   * @param listener to be triggered when the user selects a date
   * @param clock to get the <code>Calendar</code> from
   * @return the <code>DatePickerDialog</code> created instance
   */
  @NonNull
  public static DatePickerDialog createDialog(@Nullable Date date, @Nullable OnDateSetListener listener,
                                              @NonNull Clock clock) {
    Calendar calendar = DateHelper.getCalendarFromClock(clock);
    if (date != null) {
      calendar.setTime(date);
    }

    DatePickerDialog.OnDateSetListener dialogCallBack = (view, year, monthOfYear, dayOfMonth) -> {
      Date newDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
      Date newDateAndTime;
      if (date == null) {
        newDateAndTime = newDate;
      } else {
        newDateAndTime = DateHelper.setDateButKeepTime(date, newDate);
      }
      if (listener != null) {
        listener.onDateSet(newDateAndTime);
      }
    };

    int year = calendar.get(Calendar.YEAR);
    int monthOfYear = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
        dialogCallBack,
        year,
        monthOfYear,
        dayOfMonth
    );
    datePickerDialog.dismissOnPause(true);
    return datePickerDialog;
  }
}
