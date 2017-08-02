package com.xmartlabs.base.core

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.xmartlabs.bigbang.core.helper.gsonadapters.MillisecondsLocalDateAdapter
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

class MillisecondsLocalDateAdapterTest {
  companion object {
    private val DEFAULT_DATE = LocalDate.of(2017, Month.APRIL, 10)
    private val DEFAULT_DATE_LONG = 1491782400000L
  }
  
  @Test
  fun correctSerialization() {
    val adapter = MillisecondsLocalDateAdapter()
    val jsonElementExpected = JsonPrimitive(DEFAULT_DATE_LONG)

    assertThat(adapter.serialize(DEFAULT_DATE, LocalDate::class.java, null), equalTo(jsonElementExpected))
  }

  @Test
  fun correctDeserialization() {
    val actualJsonElement = JsonPrimitive(DEFAULT_DATE_LONG)
    val adapter = MillisecondsLocalDateAdapter()

    assertThat(adapter.deserialize(actualJsonElement, JsonElement::class.java, null), equalTo(DEFAULT_DATE))
  }
}
