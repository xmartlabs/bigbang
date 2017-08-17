package com.xmartlabs.bigbang.core.extensions

import android.content.Context
import android.text.format.DateFormat
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAccessor
import java.text.SimpleDateFormat
import java.util.*

/** Returns `this` number of milliseconds since the epoch */
val LocalDate.epochMilli
  get() = this.toInstant().toEpochMilli()

/** Returns a full date formatter (yyyy-MMM-dd HH:mm:ss) */
val DateTimeFormatter.completeFormat
  get() = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss", Locale.US)

/** Returns a ISO 8601 date formatter (yyyy-MM-dd'T'HH:mm:ss.SSS'Z') */
val DateTimeFormatter.isoFormat
  get() = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)

/**
 * Gets the android device time format.
 
 * @return [DateTimeFormatter] formatted the right way.
 */
val Context.deviceTimeFormat
  get() = DateTimeFormatter.ofPattern((DateFormat.getTimeFormat(this) as SimpleDateFormat).toPattern())


/**
 * Gets the android device short date format.
 
 * @return [DateTimeFormatter] formatted the right way.
 */
val Context.shortDateFormat
  get() = DateTimeFormatter.ofPattern((DateFormat.getDateFormat(this) as SimpleDateFormat).toPattern())

/**
 * Gets the android device medium date format.
 
 * @return [DateTimeFormatter] formatted the right way.
 */
val Context.mediumDateFormat
  get() = DateTimeFormatter.ofPattern((DateFormat.getMediumDateFormat(this) as SimpleDateFormat).toPattern())

/**
 * Gets the android device long date format.
 
 * @return [DateTimeFormatter] formatted the right way.
 */
val Context.longDateFormat
  get() = DateTimeFormatter.ofPattern((DateFormat.getLongDateFormat(this) as SimpleDateFormat).toPattern())

/**
 * Sets the `date` to the last second of the day it's in.
 
 * @param date the date to update.
 * *
 * @return a new [LocalDateTime] object, same day as `date` but starting at the last second of the day.
 */
val LocalDateTime.toLastSecond
  get() = this.with(LocalTime.MAX)

/**
 * Retrieves the first day of the `date` week.
 
 * @param date the date whose week's first day we want to retrieve.
 * *
 * @return a [LocalDate] instance representing the first day of the `date` week.
 */
val LocalDate.firstDayOfWeek
  get() = this.with(DayOfWeek.MONDAY)

/** Converts `this` to an Instant with `ZoneOffset` offset */
fun LocalDate.toInstant(offset: ZoneOffset = ZoneOffset.UTC) = this.atStartOfDay(offset).toInstant()

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
fun localDateTimeFromEpochMilli(milli: Long) =  localDateTimeFromEpochMilli(milli, ZoneOffset.UTC)

/** Returns a `LocalDateTime` instance with `milli` milliseconds since epoch at `ZoneOffset` offset */
fun localDateTimeFromEpochMilli(milli: Long, offset: ZoneOffset = ZoneOffset.UTC) =
    Instant.ofEpochMilli(milli).atZone(offset).toLocalDateTime()

/** Returns a `LocalDateTime` instance with `seconds` since epoch at `ZoneOffset` offset */
fun localDateTimeFromEpochSeconds(milli: Long, offset: ZoneOffset = ZoneOffset.UTC) =
    Instant.ofEpochSecond(milli).atZone(offset).toLocalDateTime()

/**
 * Converts a string representation of a date to its LocalDateTime object.
 
 * @param dateAsString Date in string format.
 * *
 * @param df           Date format.
 * *
 * @return Date.
 */
fun stringToLocalDateTime(dateAsString: String, df: DateTimeFormatter) =
    dateAsString.ignoreException { LocalDateTime.parse(this, df) }

/**
 * Checks whether the given `date` is today or not.
 
 * @param date  the date to check.
 * *
 * @param clock to be used to retrieve today's date.
 * *
 * @return true if the date given by the `clock` is the same as `date`.
 */
fun LocalDate.isToday(clock: Clock) = this == LocalDate.now(clock)

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
 * *
 * @param temporalAccessor a second local date to compare.
 * *
 * @return true if both dates have the same month and year.
 */
fun TemporalAccessor.haveSameMonthAndYearThen(temporalAccessor: TemporalAccessor) =
    YearMonth.from(this) == YearMonth.from(temporalAccessor)