package com.xmartlabs.base.core;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.xmartlabs.base.core.helper.gsonadapters.LocalDateTimeAdapter;

import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Month;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class LocalDateTimeAdapterTest {
  private static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(2017, Month.APRIL, 10, 10, 20, 30);
  private static final long DEFAULT_DATE_LONG = 1491819630000L;
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
      .withLocale(Locale.getDefault())
      .withZone(ZoneId.systemDefault());
  private static final String DEFAULT_DATE_STRING = dateTimeFormatter.format(DEFAULT_DATE.atOffset(ZoneOffset.UTC)
      .toInstant());
  private final LocalDateTimeAdapter adapter = new LocalDateTimeAdapter(dateTimeFormatter);

  @Test
  public void correctSerialization() {
    JsonElement jsonElementExpected = new JsonPrimitive(DEFAULT_DATE_STRING);

    assertThat(adapter.serialize(DEFAULT_DATE, LocalDate.class, null), equalTo(jsonElementExpected));
  }

  @Test
  public void correctSerializationWithNoTypeOrSerializationContext() {
    JsonElement jsonElementExpected = new JsonPrimitive(DEFAULT_DATE_STRING);

    assertThat(adapter.serialize(DEFAULT_DATE), equalTo(jsonElementExpected));
  }

  @Test
  public void correctDeserialization() {
    JsonElement actualJsonElement = new JsonPrimitive(DEFAULT_DATE_LONG);

    assertThat(adapter.deserialize(actualJsonElement, JsonElement.class, null), equalTo(DEFAULT_DATE));
  }

  @Test
  public void correctDeserializationWithNoTypeOrSerializationContext() {
    JsonElement actualJsonElement = new JsonPrimitive(DEFAULT_DATE_LONG);

    assertThat(adapter.deserialize(actualJsonElement), equalTo(DEFAULT_DATE));
  }
}
