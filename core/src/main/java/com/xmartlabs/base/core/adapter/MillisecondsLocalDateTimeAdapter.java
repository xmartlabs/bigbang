package com.xmartlabs.base.core.adapter;

import android.support.annotation.Nullable;

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
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.lang.reflect.Type;

import timber.log.Timber;

/** {@link Gson} type adapter that serializes {@link LocalDateTime} objects to a millisecond format */
public class MillisecondsLocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
  @Nullable
  @Override
  public synchronized JsonElement serialize(@Nullable LocalDateTime dateTime, @Nullable Type type,
                                            @Nullable JsonSerializationContext jsonSerializationContext) {
    return Optional.ofNullable(dateTime)
        .map(date -> new JsonPrimitive(date.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()))
        .orElse(null);
  }

  public synchronized JsonElement serialize(@Nullable LocalDateTime dateTime) {
    return serialize(dateTime, null, null);
  }

  @Nullable
  @Override
  public synchronized LocalDateTime deserialize(@Nullable JsonElement jsonElement, @Nullable Type type,
                                                @Nullable JsonDeserializationContext jsonDeserializationContext) {
    return Optional.ofNullable(jsonElement)
        .map(JsonElement::getAsString)
        .filter(str -> !StringUtils.isNullOrEmpty(str))
        .flatMap(str ->
            Exceptional.of(() -> Instant.ofEpochMilli(Long.valueOf(str)).atZone(ZoneOffset.UTC).toLocalDateTime())
            .ifException(e -> Timber.e(e, "Date cannot be parsed, date='%s'", str))
            .getOptional())
        .orElse(null);
  }

  @Nullable
  public synchronized LocalDateTime deserialize(@Nullable JsonElement jsonElement) {
    return deserialize(jsonElement, null, null);
  }
}
