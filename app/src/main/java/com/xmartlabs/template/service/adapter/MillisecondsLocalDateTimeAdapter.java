package com.xmartlabs.template.service.adapter;

import com.annimon.stream.Exceptional;
import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.xmartlabs.template.helper.StringUtils;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.lang.reflect.Type;

import timber.log.Timber;

/**
 * {@link Gson} type adapter that serializes {@link LocalDateTime} objects to a millisecond format
 */
public class MillisecondsLocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
  @Override
  public synchronized JsonElement serialize(LocalDateTime dateTime, Type type,
                                            JsonSerializationContext jsonSerializationContext) {
    return Optional.ofNullable(dateTime)
        .map(date -> new JsonPrimitive(date.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()))
        .orElse(null);
  }

  @Override
  public synchronized LocalDateTime deserialize(JsonElement jsonElement, Type type,
                                                JsonDeserializationContext jsonDeserializationContext) {
    return Optional.ofNullable(jsonElement.getAsString())
        .filter(str -> !StringUtils.isNullOrEmpty(str))
        .map(str -> Exceptional.of(() -> Instant.ofEpochMilli(Long.valueOf(str)).atZone(ZoneOffset.UTC).toLocalDateTime())
            .ifException(e -> Timber.e(e, "Date cannot be parsed, date='%s'", str))
            .get())
        .orElse(null);
  }
}
