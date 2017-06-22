package com.xmartlabs.bigbang.core.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.annimon.stream.Exceptional;
import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import org.threeten.bp.Clock;
import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;
import org.threeten.bp.temporal.TemporalAccessor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A helper class to conveniently parse date information.
 *
 * Based on the version of Ralf Gehrer.
 * This class contains date parsing for human and machine readable dates.
 */
@SuppressWarnings("unused")
public class DateHelper {
  private static final String DATE_COMPLETE = "yyyy-MMM-dd HH:mm:ss";
  @SuppressWarnings("SpellCheckingInspection")
  private static final String DATE_SHORT = "EEEE, MMM d";

  public static final String DATE_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  private static final DateTimeFormatter DATE_COMPLETE_FORMAT = DateTimeFormatter.ofPattern(DATE_COMPLETE, Locale.US);
  public static final DateTimeFormatter DATE_ISO_8601_FORMAT = DateTimeFormatter.ofPattern(DATE_ISO_8601, Locale.US);
  public static final DateTimeFormatter DATE_SHORT_FORMAT = DateTimeFormatter.ofPattern(DATE_SHORT, Locale.US);
  public static final DateTimeFormatter DATE_SHORT_MONTH_NAME_FORMAT = DateTimeFormatter.ofPattern("MMM", Locale.US);
  public static final DateTimeFormatter DATE_DAY_FORMAT = DateTimeFormatter.ofPattern("EEE d", Locale.US);
  public static final String DATE_SEPARATOR = " - ";

  public static final DateTimeFormatter DATE_TIME_FORMATTER_SHORT = DateTimeFormatter.ofPattern(DATE_SHORT);
  @SuppressWarnings("SpellCheckingInspection")
  public static final DateTimeFormatter DATE_TIME_FORMATTER_WEEK_DAY_LETTER = DateTimeFormatter.ofPattern("EEEEE");

  private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
  @Nullable
  private static DateTimeFormatter HOUR_MINUTE_FORMAT;

  public static DateTimeFormatter getHourMinuteFormat(Context context) {
    return Optional.ofNullable(HOUR_MINUTE_FORMAT)
        .orElse(DateFormat.is24HourFormat(context)
            ? DateTimeFormatter.ofPattern("H:mm", Locale.getDefault())
            : DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault()));
  }

  /**
   * Gets the android device time format.
   *
   * @return {@link DateTimeFormatter} formatted the right way.
   */
  public static DateTimeFormatter getDeviceTimeFormat(Context context) {
    SimpleDateFormat timeFormat = (SimpleDateFormat) DateFormat.getTimeFormat(context);
    return DateTimeFormatter.ofPattern(timeFormat.toPattern());
  }

  /**
   * Gets the android device medium date format.
   *
   * @return {@link DateTimeFormatter} formatted the right way.
   */
  public static DateTimeFormatter getDeviceMediumDateFormat(@Nullable Context context) {
    SimpleDateFormat timeFormat = (SimpleDateFormat) DateFormat.getMediumDateFormat(context);
    return DateTimeFormatter.ofPattern(timeFormat.toPattern());
  }

  /**
   * Gets the android device short date format.
   *
   * @return {@link DateTimeFormatter} formatted the right way.
   */
  public static DateTimeFormatter getDeviceShortDateFormat(@Nullable Context context) {
    SimpleDateFormat timeFormat = (SimpleDateFormat) DateFormat.getDateFormat(context);
    return DateTimeFormatter.ofPattern(timeFormat.toPattern());
  }

  /**
   * Gets the android device long date format.
   *
   * @return {@link DateTimeFormatter} formatted the right way.
   */
  public static DateTimeFormatter getDeviceLongDateFormat(@Nullable Context context) {
    SimpleDateFormat timeFormat = (SimpleDateFormat) DateFormat.getLongDateFormat(context);
    return DateTimeFormatter.ofPattern(timeFormat.toPattern());
  }

  /**
   * Converts a {@link TemporalAccessor} object to a string representation.
   *
   * @param temporalAccessor {@link TemporalAccessor} object.
   * @param df               Date format.
   * @return Date string formatted on the right way.
   */
  @NonNull
  public static String dateToString(@NonNull TemporalAccessor temporalAccessor, @NonNull DateTimeFormatter df) {
    return df.format(temporalAccessor);
  }

  /**
   * Converts a string representation of a date to its LocalDateTime object.
   *
   * @param dateAsString Date in string format.
   * @param df           Date format.
   * @return Date.
   */
  @Nullable
  public static LocalDateTime stringToLocalDateTime(@NonNull String dateAsString, @NonNull DateTimeFormatter df) {
    return Exceptional.of(() -> LocalDateTime.parse(dateAsString, df))
        .get();
  }

  /**
   * Sets the {@code date} to the last second of the day it's in.
   *
   * @param date the date to update.
   * @return a new {@link LocalDateTime} object, same day as {@code date} but starting at the last second of the day.
   */
  @NonNull
  public static LocalDateTime toLastSecond(@NonNull LocalDateTime date) {
    return date.with(LocalTime.MAX);
  }

  /**
   * Checks whether the given {@code date} is today or not.
   *
   * @param date  the date to check.
   * @param clock to be used to retrieve today's date.
   * @return true if the date given by the {@code clock} is the same as {@code date}.
   */
  public static boolean isToday(LocalDate date, Clock clock) {
    return Objects.equals(date, LocalDate.now(clock));
  }

  /**
   * Retrieves the first day of the {@code date} week.
   *
   * @param date the date whose week's first day we want to retrieve.
   * @return a {@link LocalDate} instance representing the first day of the {@code date} week.
   */
  public static LocalDate getFirstDayOfWeek(LocalDate date) {
    return date.with(DayOfWeek.MONDAY);
  }

  /**
   * Retrieves a {@link Calendar} instance from the {@code clock}.
   *
   * @param clock to be used to retrieve the {@link Calendar} instance.
   * @return a new {@link Calendar} instance representing the clock's time.
   */
  @NonNull
  public static Calendar getCalendarFromClock(Clock clock) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(clock.millis());
    return calendar;
  }

  /**
   * Retrieves a {@link Date} instance from the {@code clock}.
   *
   * @param clock to be used to retrieve the {@link Date} instance.
   * @return a new {@link Date} instance representing the clock's time.
   */
  @NonNull
  public static Date getDateFromClock(Clock clock) {
    return new Date(clock.millis());
  }

  /**
   * Checks whether or not the {@code startDate} is the same day as {@code endDate}, including midnight
   * time.
   *
   * @param startDate the date to check.
   * @param endDate   the date to check against to.
   * @return true if {@code startDate} is the same date as {@code endDate} or {@code endDate} is the
   * midnight of the next day.
   */
  public static boolean isSameDayIncludingMidnight(LocalDateTime startDate, LocalDateTime endDate) {
    return startDate.toLocalDate().equals(endDate.toLocalDate())
        || startDate.toLocalDate().equals(endDate.plusSeconds(-10).toLocalDate());
  }

  /**
   * Convert an instance of the {@link LocalDateTime} Java 8 Time backport to a {@link Date} instance.
   *
   * @param localDateTime the date to be converted.
   * @return a new {@link Date} instance representing the same date as {@code localDateTime}.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Date localDateTimeToDate(@NonNull LocalDateTime localDateTime) {
    //noinspection deprecation
    return localDateTimeToDate(localDateTime, ZoneId.systemDefault());
  }

  /**
   * Convert an instance of the {@link LocalDateTime} Java 8 Time backport to a {@link Date} instance.
   *
   * @param localDateTime the date to be converted.
   * @param zoneId        the rules to be used.
   * @return a new {@link Date} instance representing the same date as {@code localDateTime}.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Date localDateTimeToDate(@NonNull LocalDateTime localDateTime, @NonNull ZoneId zoneId) {
    //noinspection deprecation
    return localDateToDate(LocalDate.from(localDateTime), zoneId);
  }

  /**
   * Convert an instance of the {@link LocalDate} Java 8 Time backport to a {@link Date} instance.
   *
   * @param localDate the date to be converted.
   * @return a new {@link Date} instance representing the same date as {@code localDate}.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Date localDateToDate(@NonNull LocalDate localDate) {
    //noinspection deprecation
    return localDateToDate(localDate, ZoneId.systemDefault());
  }

  /**
   * Convert an instance of the {@link LocalDate} Java 8 Time backport to a {@link Date} instance,
   * using the rules given by {@code zoneId}.
   *
   * @param localDate the date to be converted.
   * @param zoneId    the rules to be used.
   * @return a new {@link Date} instance representing the same date as {@code localDate} using the {@code zoneId} rules.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Date localDateToDate(@NonNull LocalDate localDate, @NonNull ZoneId zoneId) {
    return DateTimeUtils.toDate(localDate.atStartOfDay(zoneId).toInstant());
  }

  /**
   * Convert an instance of the {@link LocalDate} Java 8 Time backport to a {@link Calendar} instance.
   *
   * @param localDate the date to be converted.
   * @return a new {@link Date} instance representing the same date as {@code localDate}.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Calendar localDateToCalendar(@NonNull LocalDate localDate) {
    //noinspection deprecation
    return localDateToCalendar(localDate, ZoneId.systemDefault());
  }

  /**
   * Convert an instance of the {@link LocalDate} Java 8 Time backport to a {@link Calendar} instance,
   * using the rules given by {@code zoneId}.
   *
   * @param localDate the date to be converted.
   * @param zoneId    the rules to be used.
   * @return a new {@link Date} instance representing the same date as {@code localDate} using the {@code zoneId} rules.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Calendar localDateToCalendar(@NonNull LocalDate localDate, @NonNull ZoneId zoneId) {
    return DateTimeUtils.toGregorianCalendar(localDate.atStartOfDay(zoneId));
  }

  /**
   * Convert an instance of the {@link LocalDateTime} Java 8 Time backport to a {@link Calendar} instance,
   * using the rules given by {@code zoneId}.
   *
   * @param localDateTime the date to be converted.
   * @param zoneId        the rules to be used.
   * @return a new {@link Date} instance representing the same date as {@code localDateTime} using the
   * {@code zoneId} rules.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Calendar localDateTimeToCalendar(@NonNull LocalDateTime localDateTime, @NonNull ZoneId zoneId) {
    //noinspection deprecation
    return localDateToCalendar(LocalDate.from(localDateTime), zoneId);
  }

  /**
   * Convert an instance of the {@link LocalDateTime} Java 8 Time backport to a {@link Calendar} instance,
   * using the rules given by {@code zoneId}.
   *
   * @param localDateTime the date to be converted.
   * @return a new {@link Date} instance representing the same date as {@code localDateTime} using the
   * {@code zoneId} rules.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Calendar localDateTimeToCalendar(@NonNull LocalDateTime localDateTime) {
    //noinspection deprecation
    return localDateTimeToCalendar(localDateTime, ZoneId.systemDefault());
  }

  /**
   * Convert a {@link Date} to an instance of Java 8 Time backport {@link LocalDate}.
   *
   * @param date the date to be converted.
   * @return a new {@link LocalDate} instance representing the same date as {@code date}.
   */
  @NonNull
  public static LocalDate dateToLocalDate(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalDate();
  }

  /**
   * Convert a {@link Date} to an instance of Java 8 Time backport {@link LocalDateTime}.
   *
   * @param date the date to be converted.
   * @return a new {@link LocalDateTime} instance representing the same date as {@code date}.
   */
  @NonNull
  public static LocalDateTime dateToLocalDateTime(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalDateTime();
  }

  /**
   * Convert a {@link Date} to an instance of Java 8 Time backport {@link LocalTime}.
   *
   * @param date the date to be converted.
   * @return a new {@link LocalTime} instance representing the same date as {@code date}.
   */
  @NonNull
  public static LocalTime dateToLocalTime(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalTime();
  }

  /**
   * Convert a {@link Calendar} to an instance of Java 8 Time backport {@link ZonedDateTime}.
   *
   * @param calendar the date to be converted.
   * @return a new {@link ZonedDateTime} instance representing the same date as {@code calendar}, using the
   * system default's {@link ZoneId}.
   */
  @NonNull
  public static ZonedDateTime calendarToZonedDateTime(@NonNull Calendar calendar) {
    return DateTimeUtils.toInstant(calendar).atZone(ZoneId.systemDefault());
  }

  /**
   * Convert a {@link Date} to an instance of Java 8 Time backport {@link ZonedDateTime}.
   *
   * @param date the date to be converted.
   * @return a new {@link ZonedDateTime} instance representing the same date as {@code date}, using the
   * system default's {@link ZoneId}.
   */
  @NonNull
  private static ZonedDateTime dateToZonedDateTime(@NonNull Date date) {
    return DateTimeUtils.toInstant(date).atZone(ZoneId.systemDefault());
  }

  /**
   * Retrieves a list of days ({@link LocalDate}s) between {@code startDate} and {@code endDate}.
   *
   * @param startDate the date before the list should start.
   * @param endDate   the date after the list should end.
   * @return the list between, but not including, {@code startDate} and {@code endDate}.
   */
  @NonNull
  public static List<LocalDate> getListOfDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
    LocalDate endLocalDate = endDate.plusDays(1);
    return Stream.iterate(startDate, day -> day.plusDays(1))
        .limit(startDate.until(endLocalDate, ChronoUnit.DAYS))
        .toList();
  }

  /**
   * Retrieves a list of days ({@link LocalDate}s) between {@code startDate} and {@code endDate}.
   *
   * @param startDate the date before the list should start.
   * @param endDate   the date after the list should end.
   * @return the list between, but not including, {@code startDate} and {@code endDate}.
   */
  @NonNull
  public static List<LocalDate> getListOfDaysBetweenTwoDates(Date startDate, Date endDate) {
    return getListOfDaysBetweenTwoDates(dateToLocalDate(startDate), dateToLocalDate(endDate));
  }
}
