package com.xmartlabs.base.core.adapter;

import com.annimon.stream.Exceptional;
import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.xmartlabs.base.core.helper.StringUtils;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneOffset;

import java.lang.reflect.Type;

import timber.log.Timber;

/** {@link Gson} type adapter that serializes {@link LocalDate} objects to a millisecond format. */
public class MillisecondsLocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  @Override
  public synchronized JsonElement serialize(LocalDate date, Type type,
                                            JsonSerializationContext jsonSerializationContext) {
    return Optional.ofNullable(date)
        .map(theDate -> new JsonPrimitive(theDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()))
        .orElse(null);
  }

  @Override
  public synchronized LocalDate deserialize(JsonElement jsonElement, Type type,
                                            JsonDeserializationContext jsonDeserializationContext) {
    return Optional.ofNullable(jsonElement.getAsString())
        .filter(str -> !StringUtils.isNullOrEmpty(str))
        .flatMap(str -> Exceptional.of(() -> Instant.ofEpochMilli(Long.valueOf(str)).atZone(ZoneOffset.UTC).toLocalDate())
            .ifException(e -> Timber.e(e, "Date cannot be parsed, date='%s'", str))
            .getOptional())
        .orElse(null);
  }
}
