package com.xmartlabs.base.core

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.xmartlabs.bigbang.core.helper.gsonadapters.MillisecondsLocalDateTimeAdapter
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Month

class MillisecondsLocalDateTimeAdapterTest {
  companion object {
    private val DEFAULT_DATE_LONG = 1491819630000L
    private val DEFAULT_DATE = LocalDateTime.of(2017, Month.APRIL, 10, 10, 20, 30)
  }
  
  @Test
  fun correctSerialization() {
    val adapter = MillisecondsLocalDateTimeAdapter()
    val jsonElementExpected = JsonPrimitive(DEFAULT_DATE_LONG)

    assertThat(adapter.serialize(DEFAULT_DATE, LocalDateTime::class.java, null), equalTo(jsonElementExpected))
  }

  @Test
  fun correctDeserialization() {
    val actualJsonElement = JsonPrimitive(DEFAULT_DATE_LONG)
    val adapter = MillisecondsLocalDateTimeAdapter()

    assertThat(adapter.deserialize(actualJsonElement, JsonElement::class.java, null), equalTo(DEFAULT_DATE))
  }
}
