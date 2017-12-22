package com.xmartlabs.bigbang.core

import com.xmartlabs.bigbang.core.extensions.datesUntil
import com.xmartlabs.bigbang.core.extensions.firstDayOfWeek
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Month
import java.util.TimeZone

class DateHelperTest {
  companion object {
    private val DEFAULT_TIME_ZONE_STRING = "GMT-03:00"
    private val DEFAULT_TIME_ZONE = TimeZone.getTimeZone(DEFAULT_TIME_ZONE_STRING)
    private val LOCAL_DATE_22nd_AUG_2016 = LocalDate.of(2016, Month.AUGUST, 22)
    private val LOCAL_DATE_29th_AUG_2016 = LocalDate.of(2016, Month.AUGUST, 29)
    private val LOCAL_DATE_MONDAY_10TH_APR_2017 = LocalDate.of(2017, Month.APRIL, 10)
    private val LOCAL_DATE_TIME_MONDAY_APRIL_10TH_10_HRS = LocalDateTime.of(2017, Month.APRIL, 10, 10, 10, 0)
  }
  
  @Before
  fun setUp() {
    TimeZone.setDefault(DEFAULT_TIME_ZONE)
  }

  @Test
  fun getListOfDaysBetweenTwoDates() {
    val expectedList = IntRange(22, 29).map { LocalDate.of(2016, Month.AUGUST, it) }

    val listOfDaysBetweenTwoDates = LOCAL_DATE_22nd_AUG_2016.datesUntil(LOCAL_DATE_29th_AUG_2016)

    assertThat<List<LocalDate>>(expectedList, equalTo<List<LocalDate>>(listOfDaysBetweenTwoDates))
  }

  @Test
  fun getListOfDaysBetweenTwoWeeks() {
    val firstDayOfWeek = LOCAL_DATE_22nd_AUG_2016
    val end = firstDayOfWeek.plusWeeks(1).plusDays(-1)
  
    val expectedList = IntRange(22, 28).map { LocalDate.of(2016, Month.AUGUST, it) }

    val listOfDaysBetweenTwoDates = firstDayOfWeek.datesUntil(end)

    assertThat<List<LocalDate>>(expectedList, equalTo<List<LocalDate>>(listOfDaysBetweenTwoDates))
  }

  @Test
  fun getFirstDayOfWeekMonday() {
    val actualMonday = LOCAL_DATE_MONDAY_10TH_APR_2017.firstDayOfWeek

    assertThat(actualMonday, equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017))
  }

  @Test
  fun getFirstDayOfWeekThursday() {
    val thursday = LocalDate.of(2017, Month.APRIL, 13)
    val actualMonday = thursday.firstDayOfWeek

    assertThat(actualMonday, equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017))
  }

  @Test
  fun getFirstDayOfWeekPreviousWeek() {
    val thursday = LocalDate.of(2017, Month.APRIL, 9)
    val actualMonday = thursday.firstDayOfWeek

    assertThat(actualMonday, not(equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017)))
  }

  @Test
  fun getFirstDayOfWeekNextWeek() {
    val tuesdayNext = LocalDate.of(2017, Month.APRIL, 18)
    val actualMonday = tuesdayNext.firstDayOfWeek

    assertThat(actualMonday, not(equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017)))
  }
}
