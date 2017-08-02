package com.xmartlabs.bigbang.core

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.xmartlabs.bigbang.core.helper.gsonadapters.LocalDateCustomFormatterAdapter
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

class LocalDateCustomFormatterAdapterTest {
  companion object {
    private val DEFAULT_DATE = LocalDate.of(2017, Month.APRIL, 10)
    private val DEFAULT_DATE_LONG = 1491782400000L
    private val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())
    private val DEFAULT_DATE_STRING = dateTimeFormatter.format(LocalDate.of(2017, Month.APRIL, 10)
        .atStartOfDay(ZoneOffset.UTC).toInstant())
  }
  
  private val adapter = LocalDateCustomFormatterAdapter(dateTimeFormatter)

  @Test
  fun correctSerialization() {
    val jsonElementExpected = JsonPrimitive(DEFAULT_DATE_STRING)

    assertThat(adapter.serialize(DEFAULT_DATE, LocalDate::class.java, null), equalTo(jsonElementExpected))
  }

  @Test
  fun correctDeserialization() {
    val actualJsonElement = JsonPrimitive(DEFAULT_DATE_LONG)

    assertThat(adapter.deserialize(actualJsonElement, JsonElement::class.java, null), equalTo(DEFAULT_DATE))
  }
}
