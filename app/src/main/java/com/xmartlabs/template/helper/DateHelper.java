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
@SuppressWarnings("unused")
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

  private static Calendar addOrRemoveGmtOffset(Calendar calendar, boolean add) {
    int factor = add ? 1 : -1;
    Date date = calendar.getTime();
    TimeZone timeZone = calendar.getTimeZone();
    long msFromEpochGmt = date.getTime();
    int offsetFromUtc = timeZone.getOffset(msFromEpochGmt);
    Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    gmtCal.setTime(date);
    gmtCal.add(Calendar.MILLISECOND, factor * offsetFromUtc);
    return gmtCal;
  }

  /**
   * Converts a <code>Calendar</code> instance to GMT time
   * @param calendar the calendar instance to be converted
   * @return a new <code>Calendar</code> instance in GTM time
   */
  public static Calendar convertToGmt(Calendar calendar) {
    return addOrRemoveGmtOffset(calendar, true);
  }

  /**
   * Converts a <code>Calendar</code> instance from GMT time
   * @param calendar the calendar instance to be converted
   * @return a new <code>Calendar</code> instance converted from GMT time
   */
  public static Calendar convertFromGmt(Calendar calendar) {
    return addOrRemoveGmtOffset(calendar, false);
  }

  /**
   * Converts a <code>Date</code> instance to GMT time
   * @param date the date instance to be converted
   * @return the <code>date</code> instance converted to GMT
   */
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

  /**
   * Sets the <code>newDate</code> year, month and day of month onto <code>oldDateAndTime</code>
   * @param oldDateAndTime the date to update
   * @param newDate the new date that will be used to update <code>oldDateAndTime</code>
   * @return a new <code>Date</code> instance with the time parameters of the <code>oldDateAndTime</code> and the
   * date parameters of the <code>newDate</code>
   */
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

  /**
   * Sets the <code>newTime</code> hour, minute, second and millisecond parameters onto <code>oldDateAndTime</code>
   * @param oldDateAndTime the date whose time parameters will be updated
   * @param newTime the new date whose time parameters will be used to update <code>oldDateAndTime</code>
   * @return a new <code>Date</code> instance with the date parameters of the <code>oldDateAndTime</code> and the
   * time parameters of the <code>newDate</code>
   */
  @NonNull
  public static Date setTimeButKeepDate(@NonNull Date oldDateAndTime, @NonNull Date newTime) {
    Calendar oldDateAndTimeCalendar = DateUtils.toCalendar(oldDateAndTime);

    Calendar newTimeCalendar = DateUtils.toCalendar(newTime);

    for (int field : Arrays.asList(Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND)) {
      oldDateAndTimeCalendar.set(field, newTimeCalendar.get(field));
    }

    return oldDateAndTimeCalendar.getTime();
  }

  /**
   * Sets the <code>date</code> to the first second of the day it's in
   * @param date the date to update
   * @return a new <code>Date</code>, same day as <code>date</code> but starting at the first second of the day
   */
  @NonNull
  public static Date toFirstSecond(@NonNull Date date) {
    return DateUtils.truncate(date, Calendar.DATE);
  }

  /**
   * Sets the <code>date</code> to the last second of the day it's in
   * @param date the date to update
   * @return a new <code>Date</code>, same day as <code>date</code> but starting at the last second of the day
   */
  @NonNull
  public static Date toLastSecond(@NonNull Date date) {
    Date followingDay = getFollowingDay(date);
    Calendar calendar = DateUtils.toCalendar(followingDay);
    calendar.add(Calendar.SECOND, -1);
    return calendar.getTime();
  }

  /**
   * Retrieves the date from the <code>clock</code> and set's it <code>toFirstSecond</code>
   * @param clock to be used to retrieve today's date
   * @return the today <code>Date</code> instance
   */
  @NonNull
  public static Date getToday(Clock clock) {
    Date now = getDateFromClock(clock);
    return toFirstSecond(now);
  }

  /**
   * Retrieves the date from the <code>clock</code> and then adds a day to it
   * @param clock to be used to retrieve tomorrow's date
   * @return the tomorrow <code>Date</code> instance
   */
  @NonNull
  public static Date getTomorrow(Clock clock) {
    Date now = getDateFromClock(clock);
    return getFollowingDay(now);
  }

  /**
   * Given a <code>Date</code>, returns the following day
   * @param date the original date
   * @return a new <code>Date</code> instance representation of the day after <code>date</code>
   */
  @NonNull
  public static Date getFollowingDay(@NonNull Date date) {
    Date today = toFirstSecond(date);
    Calendar tomorrow = DateUtils.toCalendar(today);
    tomorrow.add(Calendar.DAY_OF_YEAR, 1);
    return tomorrow.getTime();
  }

  /**
   * Retrieves the date from the <code>clock</code> and then subtract a day from it
   * @param clock to be used to retrieve tomorrow's date
   * @return the tomorrow <code>Date</code> instance
   */
  @NonNull
  public static Date getYesterday(Clock clock) {
    Date now = getDateFromClock(clock);
    return getPreviousDay(now);
  }

  /**
   * Given a <code>Date</code>, returns the previous day
   * @param date the original date
   * @return a new <code>Date</code> instance representation of the day before <code>date</code>
   */
  @NonNull
  public static Date getPreviousDay(@NonNull Date date) {
    Date today = toFirstSecond(date);
    Calendar yesterday = DateUtils.toCalendar(today);
    yesterday.add(Calendar.DAY_OF_YEAR, -1);
    return yesterday.getTime();
  }

  /**
   * Checks whether the given <code>date</code> is today or not
   * @param date the date to check
   * @param clock to be used to retrieve today's date
   * @return true if the date given by the <code>clock</code> is the same as <code>date</code>
   */
  public static boolean isToday(Date date, Clock clock) {
    Date truncatedDate = toFirstSecond(date);
    Date today = getToday(clock);
    return Objects.equals(truncatedDate, today);
  }

  /**
   * Checks whether the given <code>date</code> is yesterday or not
   * @param date the date to check
   * @param clock to be used to retrieve today's date
   * @return true if the date given by the <code>clock</code> minus one day is the same as <code>date</code>
   */
  public static boolean isYesterday(Date date, Clock clock) {
    Date truncatedDate = toFirstSecond(date);
    Date yesterday = getYesterday(clock);
    return Objects.equals(truncatedDate, yesterday);
  }

  /**
   * Checks whether the <code>startTime</code> and <code>endTime</code> are the same day, <code>startTime</code> is
   * the first second and <code>endTime</code> is the last second
   * @param startTime the start of the day
   * @param endTime the end of the day
   * @return true if <code>startTime</code> is the first second of the same day as <code>endTime</code>, which in turn
   * should be the last second of the day
   */
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

  /**
   * Retrieves the first day of the <code>date</code> week
   * @param date the date whose week's first day we want to retrieve
   * @return a <code>Date</code> instance representing the first day of the <code>date</code> week
   */
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

  /**
   * Retrieves the first day of the <code>date</code> week
   * @param date the date whose week's first day we want to retrieve
   * @return a <code>Date</code> instance representing the first day of the <code>date</code> week
   */
  public static LocalDate getFirstDayOfWeek(LocalDate date) {
    return date.with(DayOfWeek.MONDAY);
  }

  /**
   * Retrieves the last day of the <code>date</code> week
   * @param date the date whose week's last day we want to retrieve
   * @return a <code>Date</code> instance representing the last day of the <code>date</code> week
   */
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

  /**
   * Retrieves a <code>Calendar</code> instance from the <code>clock</code>
   * @param clock to be used to retrieve the <code>Calendar</code> instance
   * @return a new <code>Calendar</code> instance representing the clock's time
   */
  @NonNull
  public static Calendar getCalendarFromClock(Clock clock) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(clock.millis());
    return calendar;
  }

  /**
   * Retrieves a <code>Date</code> instance from the <code>clock</code>
   * @param clock to be used to retrieve the <code>Date</code> instance
   * @return a new <code>Date</code> omstamce representing the clock's time
   */
  @NonNull
  public static Date getDateFromClock(Clock clock) {
    return new Date(clock.millis());
  }

  /**
   * Checks whether or not the <code>startDate</code> is the same day as <code>endDate</code>, including midnight
   * time
   * @param startDate the date to check
   * @param endDate the date to check against to
   * @return true if <code>startDate</code> is the same date as <code>endDate</code> or <code>endDate</code> is the
   * midnight of the next day
   */
  public static boolean isSameDayIncludingMidnight(Date startDate, Date endDate) {
    return DateUtils.isSameDay(startDate, endDate)
        || DateUtils.isSameDay(startDate, DateUtils.addSeconds(endDate, -10));
  }

  /**
   * Creates a <code>DateFormat</code> which removes the year component from it.
   * For instance, given 10/06/2010 it yields 10/06
   *
   * Extracted from: http://stackoverflow.com/a/14015299/5170805
   * @param dateFormat the <code>DateFormat</code> from which the year component will be extracted
   * @return a new <code>DateFormat</code> in which the year is removed from the pattern
   */
  @NonNull
  public static DateFormat getRemoveYearsToDateFormat(DateFormat dateFormat) {
    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) dateFormat.clone();
    simpleDateFormat.applyPattern(simpleDateFormat.toPattern().replaceAll(
        "([^\\p{Alpha}']|('[\\p{Alpha}]+'))*y+([^\\p{Alpha}']|('[\\p{Alpha}]+'))*", ""));
    return simpleDateFormat;
  }

  /**
   * Convert an instance of the <code>LocalDate</code> Java 8 Time backport to a <code>Date</code> instance
   * @param localDate the date to be converted
   * @return a new <code>Date</code> instance representing the same date as <code>localDate</code>
   */
  @NonNull
  public static Date localDateToDate(@NonNull LocalDate localDate) {
    return localDateToDate(localDate, ZoneId.systemDefault());
  }

  /**
   * Convert an instance of the <code>LocalDate</code> Java 8 Time backport to a <code>Date</code> instance,
   * using the rules given by <code>zoneId</code>
   * @param localDate the date to be converted
   * @param zoneId the rules to be used
   * @return a new <code>Date</code> instance representing the same date as <code>localDate</code> using the
   * <code>zoneId</code> rules
   */
  @NonNull
  public static Date localDateToDate(@NonNull LocalDate localDate, @NonNull ZoneId zoneId) {
    return DateTimeUtils.toDate(localDate.atStartOfDay(zoneId).toInstant());
  }

  /**
   * Convert a <code>Date</code> to an instance of Java 8 Time backport <code>LocalDate</code>
   * @param date the date to be converted
   * @return a new <code>LocalDate</code> instance representing the same date as <code>date</code>
   */
  @NonNull
  public static LocalDate dateToLocalDate(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalDate();
  }

  /**
   * Convert a <code>Date</code> to an instance of Java 8 Time backport <code>LocalDateTime</code>
   * @param date the date to be converted
   * @return a new <code>LocalDateTime</code> instance representing the same date as <code>date</code>
   */
  @NonNull
  public static LocalDateTime dateToLocalDateTime(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalDateTime();
  }

  /**
   * Convert a <code>Calendar</code> to an instance of Java 8 Time backport <code>ZonedDateTime</code>
   * @param calendar the date to be converted
   * @return a new <code>ZonedDateTime</code> instance representing the same date as <code>calendar</code>, using the
   * system default's <code>ZoneId</code>
   */
  @NonNull
  public static ZonedDateTime calendarToZonedDateTime(@NonNull Calendar calendar) {
    return DateTimeUtils.toInstant(calendar).atZone(ZoneId.systemDefault());
  }

  /**
   * Convert a <code>Date</code> to an instance of Java 8 Time backport <code>ZonedDateTime</code>
   * @param date the date to be converted
   * @return a new <code>ZonedDateTime</code> instance representing the same date as <code>date</code>, using the
   * system default's <code>ZoneId</code>
   */
  @NonNull
  private static ZonedDateTime dateToZonedDateTime(@NonNull Date date) {
    return DateTimeUtils.toInstant(date).atZone(ZoneId.systemDefault());
  }

  /**
   * Retrieves a list of days (<code>LocalDate</code>s) between <code>startDate</code> and <code>endDate</code>
   * @param startDate the date before the list should start
   * @param endDate the date after the list should end
   * @return the list between, but not including, <code>startDate</code> and <code>endDate</code>
   */
  @NonNull
  public static List<LocalDate> getListOfDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
    LocalDate endLocalDate = endDate.plusDays(1);
    return Stream.iterate(startDate, day -> day.plusDays(1))
        .limit(startDate.until(endLocalDate, ChronoUnit.DAYS))
        .collect(Collectors.toList());
  }

  /**
   * Retrieves a list of days (<code>LocalDate</code>s) between <code>startDate</code> and <code>endDate</code>
   * @param startDate the date before the list should start
   * @param endDate the date after the list should end
   * @return the list between, but not including, <code>startDate</code> and <code>endDate</code>
   */
  @NonNull
  public static List<LocalDate> getListOfDaysBetweenTwoDates(Date startDate, Date endDate) {
    return getListOfDaysBetweenTwoDates(dateToLocalDate(startDate), dateToLocalDate(endDate));
  }
}
