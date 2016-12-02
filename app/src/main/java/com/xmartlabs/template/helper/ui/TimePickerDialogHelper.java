package com.xmartlabs.template.helper.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.threeten.bp.Clock;
import org.threeten.bp.LocalTime;

/**
 * Created by medina on 19/09/2016.
 */
@SuppressWarnings("unused")
public class TimePickerDialogHelper {
  /**
   * Creates a <code>TimePickerDialog</code> instance without specifying the initial date
   *
   * @param context  to retrieve the time format (12 or 24 hour)
   * @param listener to listen for a date selection
   * @param clock    to retrieve the calendar from
   * @return a new instance of <code>TimePickerDialog</code>
   */
  @NonNull
  @SuppressWarnings("unused")
  public static TimePickerDialog createDialog(@NonNull Context context, @Nullable OnLocalTimeSetListener listener, @NonNull Clock clock) {
    return createDialog(context, null, listener, clock);
  }

  /**
   * Creates a <code>TimePickerDialog</code> instance
   *
   * @param context   to retrieve the time format (12 or 24 hour)
   * @param localTime the initial localTime
   * @param listener  to listen for a localTime selection
   * @param clock     to retrieve the calendar from
   * @return a new instance of <code>TimePickerDialog</code>
   */
  @NonNull
  public static TimePickerDialog createDialog(@NonNull Context context, @Nullable LocalTime localTime, @Nullable OnLocalTimeSetListener listener, @NonNull Clock clock) {
    TimePickerDialog.OnTimeSetListener dialogCallBack = (view, hourOfDay, minute, second) -> {
      LocalTime time = LocalTime.of(hourOfDay, minute, second);
      if (listener != null) {
        listener.onTimeSet(time);
      }
    };

    if (localTime == null) {
      localTime = LocalTime.now(clock);
    }
    TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
        dialogCallBack,
        localTime.getHour(),
        localTime.getMinute(),
        localTime.getSecond(),
        DateFormat.is24HourFormat(context)
    );
    timePickerDialog.dismissOnPause(true);
    return timePickerDialog;
  }
}
