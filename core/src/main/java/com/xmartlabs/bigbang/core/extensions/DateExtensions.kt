@file:Suppress("unused")

package com.xmartlabs.bigbang.core.extensions

import android.content.Context
import android.text.format.DateFormat
import org.threeten.bp.Clock
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.Period
import org.threeten.bp.YearMonth
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAccessor
import java.text.SimpleDateFormat
import java.util.Locale

object DefaultDateTimeFormatter{
  /** Returns a full date formatter (yyyy-MMM-dd HH:mm:ss) */
  val COMPLETEFORMAT = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss", Locale.US)

  /** Returns a ISO 8601 date formatter (yyyy-MM-dd'T'HH:mm:ss.SSS'Z') */
  val ISOFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
}

/** Returns `this` number of milliseconds since the epoch */
val LocalDate.epochMilli
  get() = this.toInstant().toEpochMilli()

/**
 * Gets the android device time format.
 *
 * @return [DateTimeFormatter] formatted the right way.
 */
val Context.deviceTimeFormat
  get() = DateTimeFormatter.ofPattern((DateFormat.getTimeFormat(this) as SimpleDateFormat).toPattern())


/**
 * Gets the android device short date format.
 *
 * @return [DateTimeFormatter] formatted the right way.
 */
val Context.shortDateFormat
  get() = DateTimeFormatter.ofPattern((DateFormat.getDateFormat(this) as SimpleDateFormat).toPattern())

/**
 * Gets the android device medium date format.
 *
 * @return [DateTimeFormatter] formatted the right way.
 */
val Context.mediumDateFormat
  get() = DateTimeFormatter.ofPattern((DateFormat.getMediumDateFormat(this) as SimpleDateFormat).toPattern())

/**
 * Gets the android device long date format.
 *
 * @return [DateTimeFormatter] formatted the right way.
 */
val Context.longDateFormat
  get() = DateTimeFormatter.ofPattern((DateFormat.getLongDateFormat(this) as SimpleDateFormat).toPattern())

/**
 * Sets the `date` to the last second of the day it's in.
 *
 * @param date the date to update.
 *
 * @return a new [LocalDateTime] object, same day as `date` but starting at the last second of the day.
 */
val LocalDateTime.toLastSecond
  get() = this.with(LocalTime.MAX)

/**
 * Retrieves the first day of the `date` week.
 *
 * @param date the date whose week's first day we want to retrieve.
 *
 * @return a [LocalDate] instance representing the first day of the `date` week.
 */
val LocalDate.firstDayOfWeek
  get() = this.with(DayOfWeek.MONDAY)

/** Converts `this` to an Instant with `ZoneOffset` offset */
fun LocalDate.toInstant(offset: ZoneOffset = ZoneOffset.UTC) = atStartOfDay(offset).toInstant()

/** Returns `this` number of seconds since the epoch */
fun LocalDate.epochSeconds(offset: ZoneOffset = ZoneOffset.UTC) = this.atStartOfDay().toEpochSecond(offset)

/** Returns a `LocalDate` instance with `milli` milliseconds since epoch */
fun localDatefromEpochMilli(milli: Long) = localDatefromEpochMilli(milli, ZoneOffset.UTC)

/** Returns a `LocalDate` instance with `milli` milliseconds since epoch at `ZoneOffset` offset */
fun localDatefromEpochMilli(milli: Long, offset: ZoneOffset = ZoneOffset.UTC) =
    Instant.ofEpochMilli(milli).atZone(offset).toLocalDate()

/** Returns a `LocalDate` instance with `seconds` since epoch */
fun localDateFromEpochSeconds(seconds: Long, offset: ZoneOffset = ZoneOffset.UTC) =
    Instant.ofEpochSecond(seconds).atZone(offset).toLocalDate()

/** Returns a `LocalDateTime` instance with `milli` milliseconds since epoch */
fun localDateTimeFromEpochMilli(milli: Long) = localDateTimeFromEpochMilli(milli, ZoneOffset.UTC)

/** Returns a `LocalDateTime` instance with `milli` milliseconds since epoch at `ZoneOffset` offset */
fun localDateTimeFromEpochMilli(milli: Long, offset: ZoneOffset = ZoneOffset.UTC) =
    Instant.ofEpochMilli(milli).atZone(offset).toLocalDateTime()

/** Returns a `LocalDateTime` instance with `seconds` since epoch at `ZoneOffset` offset */
fun localDateTimeFromEpochSeconds(seconds: Long, offset: ZoneOffset = ZoneOffset.UTC) =
    Instant.ofEpochSecond(seconds).atZone(offset).toLocalDateTime()

/**
 * Converts a string representation of a date to its LocalDateTime object.
 *
 * @param dateAsString Date in string format.
 * @param df           Date format.
 * *
 * @return Date.
 */
fun stringToLocalDateTime(dateAsString: String, df: DateTimeFormatter) =
    dateAsString.ignoreException { LocalDateTime.parse(this, df) }

/**
 * Checks whether the given `date` is today or not.
 *
 * @param date  the date to check.
 * @param clock to be used to retrieve today's date.
 *
 * @return true if the date given by the `clock` is the same as `date`.
 */
fun LocalDate.isToday(clock: Clock = Clock.systemDefaultZone()) = this == baseDate(clock)

/**
 * Retrieves a list of days ([LocalDate]s) between `startDate` and `endDate`.

 * @param startDate the date before the list should start.
 * *
 * @param endDate   the date after the list should end.
 * *
 * @return the list between, but not including, `startDate` and `endDate`.
 */
fun LocalDate.datesUntil(date: LocalDate) = IntRange(0, this.until(date).days)
    .map(Int::toLong)
    .map(this::plusDays)

/**
 * Checks whether the given `date` is today or not.
 * @param temporalAccessor a second local date to compare.
 *
 * @return true if both dates have the same month and year.
 */
fun TemporalAccessor.haveSameMonthAndYearThan(temporalAccessor: TemporalAccessor) =
    YearMonth.from(this) == YearMonth.from(temporalAccessor)

/**
 * Converts the integer expressed in nanoseconds to duration.
 *
 * @return The time expressed in duration.
 */
val Int.nanoseconds: Duration
  get() = Duration.ofNanos(toLong())

/**
 * Converts the integer expressed in microseconds to duration.
 *
 * @return The time expressed in duration.
 */
val Int.microseconds: Duration
  get() = Duration.ofNanos(toLong() * 1000L)

/**
 * Converts the integer expressed in milliseconds to duration.
 *
 * @return The time expressed in duration.
 */
val Int.milliseconds: Duration
  get() = Duration.ofMillis(toLong())

/**
 * Converts the integer expressed in seconds to duration.
 *
 * @return The time expressed in duration.
 */
val Int.seconds: Duration
  get() = Duration.ofSeconds(toLong())

/**
 * Converts the integer expressed in minutes to duration.
 *
 * @return The time expressed in duration.
 */
val Int.minutes: Duration
  get() = Duration.ofMinutes(toLong())

/**
 * Converts the integer expressed in hours to duration.
 *
 * @return The time expressed in duration.
 */
val Int.hours: Duration
  get() = Duration.ofHours(toLong())

/**
 * Converts the integer expressed in days to a period of time.
 *
 * @return The time expressed in a period.
 */
val Int.days: Period
  get() = Period.ofDays(this)

/**
 * Converts the integer expressed in weeks to a period of time.
 *
 * @return The time expressed in a period.
 */
val Int.weeks: Period
  get() = Period.ofWeeks(this)

/**
 * Converts the integer expressed in months to a period of time.
 *
 * @return The time expressed in a period.
 */
val Int.months: Period
  get() = Period.ofMonths(this)

/**
 * Converts the integer expressed in years to a period of time.
 *
 * @return The time expressed in a period.
 */
val Int.years: Period
  get() = Period.ofYears(this)

/**
 * Gets the LocalDateTime calculated as [current_date - duration].
 *
 * @return The LocalDateTime.
 */
val Duration.ago: LocalDateTime
  get() = baseTime() - this

/**
 * Gets the LocalDateTime calculated as [current_date + duration].
 *
 * @return The LocalDateTime.
 */
val Duration.fromNow: LocalDateTime
  get() = baseTime() + this

/**
 * Gets the LocalDateTime calculated as [current_date - period].
 *
 * @return The LocalDateTime.
 */
val Period.ago: LocalDate
  get() = baseDate() - this

/**
 * Gets the LocalDateTime calculated as [current_date + period].
 *
 * @return The LocalDateTime.
 */
val Period.fromNow: LocalDate
  get() = baseDate() + this

private fun baseDate(clock: Clock = Clock.systemDefaultZone()) = LocalDate.now(clock)

private fun baseTime(clock: Clock = Clock.systemDefaultZone()) = LocalDateTime.now(clock)
