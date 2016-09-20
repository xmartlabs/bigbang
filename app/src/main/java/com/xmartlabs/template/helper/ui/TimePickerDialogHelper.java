package com.xmartlabs.template.helper.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.xmartlabs.template.helper.DateHelper;

import org.threeten.bp.Clock;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import lombok.Setter;

/**
 * Created by medina on 19/09/2016.
 */
public class TimePickerDialogHelper {
  @Setter
  OnDateSetListener listener;

  /**
   * Creates a <code>TimePickerDialog</code> instance without specifying the initial date
   * @param context to retrieve the time format (12 or 24 hour)
   * @param listener to listen for a date selection
   * @param clock to retrieve the calendar from
   * @return a new instance of <code>TimePickerDialog</code>
   */
  @NonNull
  @SuppressWarnings("unused")
  public static TimePickerDialog createDialog(@NonNull Context context, @Nullable OnDateSetListener listener, @NonNull Clock clock) {
    return createDialog(context, null, listener, clock);
  }

  /**
   * Creates a <code>TimePickerDialog</code> instance
   * @param context to retrieve the time format (12 or 24 hour)
   * @param date the initial date
   * @param listener to listen for a date selection
   * @param clock to retrieve the calendar from
   * @return a new instance of <code>TimePickerDialog</code>
   */
  @NonNull
  public static TimePickerDialog createDialog(@NonNull Context context, @Nullable Date date, @Nullable OnDateSetListener listener, @NonNull Clock clock) {
    Calendar calendar = DateHelper.getCalendarFromClock(clock);
    if (date != null) {
      calendar.setTime(date);
    }

    TimePickerDialog.OnTimeSetListener dialogCallBack = (view, hourOfDay, minute, second) -> {
      Date newTime = new GregorianCalendar(0, 0, 0, hourOfDay, minute, second).getTime();
      Date newDateAndTime;
      if (date == null) {
        newDateAndTime = newTime;
      } else {
        newDateAndTime = DateHelper.setTimeButKeepDate(date, newTime);
      }
      if (listener != null) {
        listener.onDateSet(newDateAndTime);
      }
    };

    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

    TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
        dialogCallBack,
        hourOfDay,
        minute,
        second,
        DateFormat.is24HourFormat(context)
    );
    timePickerDialog.dismissOnPause(true);
    return timePickerDialog;
  }
}
