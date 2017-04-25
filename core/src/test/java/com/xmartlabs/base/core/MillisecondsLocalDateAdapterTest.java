package com.xmartlabs.base.core;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.xmartlabs.base.core.helper.gsonadapters.MillisecondsLocalDateAdapter;

import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MillisecondsLocalDateAdapterTest {
  private static final LocalDate DEFAULT_DATE = LocalDate.of(2017, Month.APRIL, 10);
  private static final long DEFAULT_DATE_LONG = 1491782400000L;

  @Test
  public void correctSerialization() {
    MillisecondsLocalDateAdapter adapter = new MillisecondsLocalDateAdapter();
    JsonElement jsonElementExpected = new JsonPrimitive(DEFAULT_DATE_LONG);

    assertThat(adapter.serialize(DEFAULT_DATE, LocalDate.class, null), equalTo(jsonElementExpected));
  }

  @Test
  public void correctDeserialization() {
    JsonElement actualJsonElement = new JsonPrimitive(DEFAULT_DATE_LONG);
    MillisecondsLocalDateAdapter adapter = new MillisecondsLocalDateAdapter();

    assertThat(adapter.deserialize(actualJsonElement, JsonElement.class, null), equalTo(DEFAULT_DATE));
  }
}
