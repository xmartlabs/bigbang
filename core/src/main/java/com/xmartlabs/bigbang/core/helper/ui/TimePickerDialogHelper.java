package com.xmartlabs.bigbang.core.helper.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.annimon.stream.Optional;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.xmartlabs.bigbang.core.helper.function.Consumer;

import org.threeten.bp.Clock;
import org.threeten.bp.LocalTime;

@SuppressWarnings("unused")
public class TimePickerDialogHelper {
  /**
   * Creates a {@link TimePickerDialog} instance without specifying the initial date.
   *
   * @param context  to retrieve the time format (12 or 24 hour)
   * @param listener to listen for a date selection
   * @param clock    to retrieve the calendar from
   * @return a new instance of {@link TimePickerDialog}
   */
  @NonNull
  @SuppressWarnings("unused")
  public static TimePickerDialog createDialog(@NonNull Context context, @Nullable Consumer<LocalTime> listener,
                                              @NonNull Clock clock) {
    return createDialog(context, null, listener, clock);
  }

  /**
   * Creates a {@link TimePickerDialog} instance.
   *
   * @param context   to retrieve the time format (12 or 24 hour)
   * @param localTime the initial localTime
   * @param listener  to listen for a localTime selection
   * @param clock     to retrieve the calendar from
   * @return a new instance of {@link TimePickerDialog}
   */
  @NonNull
  public static TimePickerDialog createDialog(@NonNull Context context, @Nullable LocalTime localTime,
                                              @Nullable Consumer<LocalTime> listener, @NonNull Clock clock) {
    TimePickerDialog.OnTimeSetListener dialogCallBack = (view, hourOfDay, minute, second) -> {
      LocalTime time = LocalTime.of(hourOfDay, minute, second);
      Optional.ofNullable(listener)
          .ifPresent(theListener -> theListener.accept(time));
    };

    localTime = Optional.ofNullable(localTime)
        .orElse(LocalTime.now(clock));
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
