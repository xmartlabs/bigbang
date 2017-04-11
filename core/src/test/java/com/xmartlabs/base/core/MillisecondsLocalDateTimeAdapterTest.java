package com.xmartlabs.base.core;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.xmartlabs.base.core.service.adapter.MillisecondsLocalDateTimeAdapter;

import org.junit.Test;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MillisecondsLocalDateTimeAdapterTest {
  private static final long DEFAULT_DATE_LONG = 1491819630000L;
  private static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(2017, Month.APRIL, 10, 10, 20, 30);

  @Test
  public void correctSerialization() {
    MillisecondsLocalDateTimeAdapter adapter = new MillisecondsLocalDateTimeAdapter();
    JsonElement jsonElementExpected = new JsonPrimitive(DEFAULT_DATE_LONG);

    assertThat(adapter.serialize(DEFAULT_DATE, LocalDateTime.class, null), equalTo(jsonElementExpected));
  }

  @Test
  public void correctSerializationWithNoTypeOrSerializationContext() {
    MillisecondsLocalDateTimeAdapter adapter = new MillisecondsLocalDateTimeAdapter();
    JsonElement jsonElementExpected = new JsonPrimitive(DEFAULT_DATE_LONG);

    assertThat(adapter.serialize(DEFAULT_DATE), equalTo(jsonElementExpected));
  }

  @Test
  public void correctDeserialization() {
    JsonElement actualJsonElement = new JsonPrimitive(DEFAULT_DATE_LONG);
    MillisecondsLocalDateTimeAdapter adapter = new MillisecondsLocalDateTimeAdapter();

    assertThat(adapter.deserialize(actualJsonElement, JsonElement.class, null), equalTo(DEFAULT_DATE));
  }

  @Test
  public void correctDeserializationWithNoTypeOrSerializationContext() {
    JsonElement actualJsonElement = new JsonPrimitive(DEFAULT_DATE_LONG);
    MillisecondsLocalDateTimeAdapter adapter = new MillisecondsLocalDateTimeAdapter();

    assertThat(adapter.deserialize(actualJsonElement), equalTo(DEFAULT_DATE));
  }
}
