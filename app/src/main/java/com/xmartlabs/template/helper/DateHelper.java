package com.xmartlabs.template.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.template.BaseProjectApplication;

import org.apache.commons.lang3.time.DateUtils;
import org.threeten.bp.Clock;
import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by santiago on 18/09/15.
 * A helper class to conveniently parse date information.
 * Based on the version of Ralf Gehrer <ralf@ecotastic.de>
 * This class contains date parsing for human and machine readable dates.
 */
public class DateHelper {
  private static final String DATE_COMPLETE = "yyyy-MMM-dd HH:mm:ss";
  @SuppressWarnings("SpellCheckingInspection")
  private static final String DATE_SHORT = "EEEE, MMM d";

  public static final String DATE_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final DateFormat TIME_FORMAT = android.text.format.DateFormat.getTimeFormat(BaseProjectApplication.getContext());
  private static final DateFormat DATE_COMPLETE_UTC = new SimpleDateFormat(DATE_COMPLETE, Locale.US);
  private static final DateFormat DATE_COMPLETE_LOCAL = new SimpleDateFormat(DATE_COMPLETE, Locale.US);
  public static final DateFormat DATE_LONG_FORMAT = android.text.format.DateFormat.getLongDateFormat(BaseProjectApplication.getContext());
  public static final DateFormat DATE_ISO_8601_FORMAT = new SimpleDateFormat(DATE_ISO_8601, Locale.US);
  public static final DateFormat DATE_SHORT_FORMAT = new SimpleDateFormat(DATE_SHORT, Locale.US);
  public static final DateFormat DATE_SHORT_MONTH_NAME_FORMAT = new SimpleDateFormat("MMM", Locale.US);
  public static final DateFormat DATE_DAY_FORMAT = new SimpleDateFormat("EEE d", Locale.US);
  public static final String DATE_SEPARATOR = " - ";

  public static final DateTimeFormatter DATE_TIME_FORMATTER_SHORT = DateTimeFormatter.ofPattern(DATE_SHORT);
  @SuppressWarnings("SpellCheckingInspection")
  public static final DateTimeFormatter DATE_TIME_FORMATTER_WEEK_DAY_LETTER = DateTimeFormatter.ofPattern("EEEEE");

  private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
  public static final DateFormat HOUR_MINUTE_FORMAT = android.text.format.DateFormat.is24HourFormat(BaseProjectApplication.getContext())
      ? new SimpleDateFormat("H:mm", Locale.getDefault())
      : new SimpleDateFormat("h:mm a", Locale.getDefault());

  static {
    DATE_COMPLETE_UTC.setTimeZone(UTC);
    DATE_ISO_8601_FORMAT.setTimeZone(UTC);
  }

  /**
   * Converts a Date object to a string representation in UTC
   *
   * @param date Date object
   * @param df   Date format
   * @return Date string formatted on the right way in UTC
   */
  @Nullable
  public static String dateToStringUTC(@Nullable Date date, @NonNull DateFormat df) {
    if (date == null) {
      return null;
    } else {
      df.setTimeZone(UTC);
      return dateToString(date, df);
    }
  }

  private static Calendar addOrRemoveGmtOffset(Calendar cal, boolean add) {
    int factor = add ? 1 : -1;
    Date date = cal.getTime();
    TimeZone timeZone = cal.getTimeZone();
    long msFromEpochGmt = date.getTime();
    int offsetFromUtc = timeZone.getOffset(msFromEpochGmt);
    Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    gmtCal.setTime(date);
    gmtCal.add(Calendar.MILLISECOND, factor * offsetFromUtc);
    return gmtCal;
  }

  public static Calendar convertToGmt(Calendar cal) {
    return addOrRemoveGmtOffset(cal, true);
  }

  public static Calendar convertFromGmt(Calendar cal) {
    return addOrRemoveGmtOffset(cal, false);
  }

  @NonNull
  public static Date convertToGmt(Date date) {
    try {
      return DATE_COMPLETE_LOCAL.parse(DATE_COMPLETE_UTC.format(date));
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Converts a Date object to a string representation
   *
   * @param date Date object
   * @param df   Date format
   * @return Date string formatted on the right way
   */
  @NonNull
  public static String dateToString(@NonNull Date date, @NonNull DateFormat df) {
    return df.format(date);
  }

  /**
   * Converts a string representation of a date to its Date object.
   *
   * @param dateAsString Date in string format
   * @param df           Date format
   * @return Date
   */
  @Nullable
  public static Date stringToDate(@NonNull String dateAsString, @NonNull DateFormat df) {
    try {
      df.setTimeZone(UTC);
      return df.parse(dateAsString);
    } catch (ParseException e) {
      return null;
    }
  }

  @NonNull
  public static Date setDateButKeepTime(@NonNull Date oldDateAndTime, @NonNull Date newDate) {
    Calendar oldDateAndTimeCalendar = DateUtils.toCalendar(oldDateAndTime);

    Calendar newDateCalendar = DateUtils.toCalendar(newDate);

    oldDateAndTimeCalendar.set(
        newDateCalendar.get(Calendar.YEAR),
        newDateCalendar.get(Calendar.MONTH),
        newDateCalendar.get(Calendar.DAY_OF_MONTH)
    );

    return oldDateAndTimeCalendar.getTime();
  }

  @NonNull
  public static Date setTimeButKeepDate(@NonNull Date oldDateAndTime, @NonNull Date newTime) {
    //return setDateButKeepTime(newTime, oldDateAndTime); // This could keep more information from newTime than desired.

    Calendar oldDateAndTimeCalendar = DateUtils.toCalendar(oldDateAndTime);

    Calendar newTimeCalendar = DateUtils.toCalendar(newTime);

    for (int field : Arrays.asList(Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND)) {
      oldDateAndTimeCalendar.set(field, newTimeCalendar.get(field));
    }

    return oldDateAndTimeCalendar.getTime();
  }

  @NonNull
  public static Date toFirstSecond(@NonNull Date date) {
    return DateUtils.truncate(date, Calendar.DATE);
  }

  @NonNull
  public static Date toLastSecond(@NonNull Date date) {
    Date followingDay = getFollowingDay(date);
    Calendar calendar = DateUtils.toCalendar(followingDay);
    calendar.add(Calendar.SECOND, -1);
    return calendar.getTime();
  }

  @NonNull
  public static Date getToday(Clock clock) {
    Date now = getDateFromClock(clock);
    return toFirstSecond(now);
  }

  @NonNull
  public static Date getTomorrow(Clock clock) {
    Date now = getDateFromClock(clock);
    return getFollowingDay(now);
  }

  @NonNull
  public static Date getFollowingDay(@NonNull Date date) {
    Date today = toFirstSecond(date);
    Calendar tomorrow = DateUtils.toCalendar(today);
    tomorrow.add(Calendar.DAY_OF_YEAR, 1);
    return tomorrow.getTime();
  }

  @NonNull
  public static Date getYesterday(Clock clock) {
    Date now = getDateFromClock(clock);
    return getPreviousDay(now);
  }

  @NonNull
  public static Date getPreviousDay(@NonNull Date date) {
    Date today = toFirstSecond(date);
    Calendar yesterday = DateUtils.toCalendar(today);
    yesterday.add(Calendar.DAY_OF_YEAR, -1);
    return yesterday.getTime();
  }

  public static boolean isToday(Date date, Clock clock) {
    Date truncatedDate = toFirstSecond(date);
    Date today = getToday(clock);
    return Objects.equals(truncatedDate, today);
  }

  public static boolean isYesterday(Date date, Clock clock) {
    Date truncatedDate = toFirstSecond(date);
    Date yesterday = getYesterday(clock);
    return Objects.equals(truncatedDate, yesterday);
  }

  public static boolean isAllDay(Calendar startTime, Calendar endTime) {
    startTime = DateUtils.truncate(startTime, Calendar.MINUTE);
    endTime = DateUtils.truncate(endTime, Calendar.MINUTE);
    Calendar startDateTime = DateUtils.truncate(startTime, Calendar.DATE);
    Calendar endDateTime = (Calendar) endTime.clone();
    endDateTime.set(Calendar.HOUR_OF_DAY, 23);
    endDateTime.set(Calendar.MINUTE, 59);

    return DateUtils.isSameDay(startTime, endTime)
        && startDateTime.equals(startTime)
        && !endTime.before(endDateTime);
  }

  public static Date getFirstDayOfWeek(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

  public static LocalDate getFirstDayOfWeek(LocalDate date) {
    return date.with(DayOfWeek.MONDAY);
  }

  public static Date getLastDayOfWeek(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    return calendar.getTime();
  }

  @NonNull
  public static Calendar getCalendarFromClock(Clock clock) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(clock.millis());
    return calendar;
  }

  @NonNull
  public static Date getDateFromClock(Clock clock) {
    return new Date(clock.millis());
  }

  @NonNull
  public static String getFriendlyStringForDateRange(Date startDate, Date endDate) {
    if (isSameDayIncludingMidnight(startDate, endDate)) {
      return DATE_SHORT_FORMAT.format(startDate);
    } else if (Objects.equals(DateUtils.truncate(startDate, Calendar.MONTH), DateUtils.truncate(endDate, Calendar.MONTH))) {
      return String.format(
          Locale.getDefault(),
          "%s%s%s %s",
          DATE_DAY_FORMAT.format(startDate),
          DATE_SEPARATOR,
          DATE_DAY_FORMAT.format(endDate),
          DATE_SHORT_MONTH_NAME_FORMAT.format(startDate)
      );
    } else {
      return String.format(
          Locale.getDefault(),
          "%s%s%s",
          DATE_SHORT_FORMAT.format(startDate),
          DATE_SEPARATOR,
          DATE_SHORT_FORMAT.format(endDate)
      );
    }
  }

  public static boolean isSameDayIncludingMidnight(Date startDate, Date endDate) {
    return DateUtils.isSameDay(startDate, endDate)
        || DateUtils.isSameDay(startDate, DateUtils.addSeconds(endDate, -10));
  }

  // http://stackoverflow.com/a/14015299/5170805
  @NonNull
  public static DateFormat getRemoveYearsToDateFormat(DateFormat dateFormat) {
    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) dateFormat.clone();
    simpleDateFormat.applyPattern(simpleDateFormat.toPattern().replaceAll(
        "([^\\p{Alpha}']|('[\\p{Alpha}]+'))*y+([^\\p{Alpha}']|('[\\p{Alpha}]+'))*", ""));
    return simpleDateFormat;
  }

  @NonNull
  public static Date localDateToDate(@NonNull LocalDate localDate) {
    return localDateToDate(localDate, ZoneId.systemDefault());
  }

  @NonNull
  public static Date localDateToDate(@NonNull LocalDate localDate, @NonNull ZoneId zoneId) {
    return DateTimeUtils.toDate(localDate.atStartOfDay(zoneId).toInstant());
  }

  @NonNull
  public static LocalDate dateToLocalDate(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalDate();
  }

  @NonNull
  public static LocalDateTime dateToLocalDateTime(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalDateTime();
  }

  @NonNull
  public static ZonedDateTime calendarToZonedDateTime(@NonNull Calendar calendar) {
    return DateTimeUtils.toInstant(calendar).atZone(ZoneId.systemDefault());
  }

  @NonNull
  private static ZonedDateTime dateToZonedDateTime(@NonNull Date date) {
    return DateTimeUtils.toInstant(date).atZone(ZoneId.systemDefault());
  }

  @NonNull
  public static List<LocalDate> getListOfDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
    LocalDate endLocalDate = endDate.plusDays(1);
    return Stream.iterate(startDate, day -> day.plusDays(1))
        .limit(startDate.until(endLocalDate, ChronoUnit.DAYS))
        .collect(Collectors.toList());
  }

  @NonNull
  public static List<LocalDate> getListOfDaysBetweenTwoDates(Date startDate, Date endDate) {
    return getListOfDaysBetweenTwoDates(dateToLocalDate(startDate), dateToLocalDate(endDate));
  }

  @CheckResult
  @NonNull
  public static String durationToHoursMinutesString(Duration duration) {
    long hours = duration.toHours();
    long minutes = duration.minusHours(hours).toMinutes();
    if (minutes == 0) {
      return String.format(Locale.getDefault(), "%dh", hours);
    } else {
      return String.format(Locale.getDefault(), "%dh %dm", hours, minutes);
    }
  }

  public static boolean startAndEndDateShouldBeFromSameDay(Date startTime, Date endTime) {
    final Calendar startTimeCalendar = Calendar.getInstance();
    startTimeCalendar.setTime(startTime);
    final Calendar endTimeCalendar = Calendar.getInstance();
    endTimeCalendar.setTime(endTime);

    int startTimeHour = startTimeCalendar.get(Calendar.HOUR_OF_DAY);
    int endTimeHour = endTimeCalendar.get(Calendar.HOUR_OF_DAY);

    if (startTimeHour == endTimeHour) {
      int startTimeMinute = startTimeCalendar.get(Calendar.MINUTE);
      int endTimeHourMinute = endTimeCalendar.get(Calendar.MINUTE);
      if (startTimeMinute >= endTimeHourMinute) {
        return false;
      }
    } else if (startTimeHour > endTimeHour) {
      return false;
    }

    return true;
  }
}
