package com.xmartlabs.bigbang.core

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.xmartlabs.bigbang.core.extensions.DefaultDateTimeFormatter
import com.xmartlabs.bigbang.core.helper.gsonadapters.LocalDateTimeCustomFormatterAdapter
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Month
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import java.util.Locale

class LocalDateTimeCustomFormatterAdapterTest {
  companion object {
    private val DEFAULT_DATE = LocalDateTime.of(2017, Month.APRIL, 10, 10, 20, 30)
    private val DEFAULT_DATE_STRING_VALUE = DefaultDateTimeFormatter.ISO_8601.format(DEFAULT_DATE)
    private val dateTimeFormatter = DefaultDateTimeFormatter.ISO_8601
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())
    private val DEFAULT_DATE_STRING = dateTimeFormatter.format(DEFAULT_DATE.atOffset(ZoneOffset.UTC)
        .toInstant())
  }
  
  private val adapter = LocalDateTimeCustomFormatterAdapter(dateTimeFormatter)

  @Test
  fun correctSerialization() {
    val jsonElementExpected = JsonPrimitive(DEFAULT_DATE_STRING)

    assertThat(adapter.serialize(DEFAULT_DATE, LocalDate::class.java, null), equalTo(jsonElementExpected))
  }

  @Test
  fun correctDeserialization() {
    val actualJsonElement = JsonPrimitive(DEFAULT_DATE_STRING_VALUE)

    assertThat(adapter.deserialize(actualJsonElement, JsonElement::class.java, null), equalTo(DEFAULT_DATE))
  }
}
