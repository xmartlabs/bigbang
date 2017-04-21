package com.xmartlabs.base.core.helper.gsonadapters;

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
import java.text.DateFormat;

import lombok.RequiredArgsConstructor;
import timber.log.Timber;

/** {@link Gson} type adapter that serializes {@link LocalDate} objects to any specified format. */
@RequiredArgsConstructor
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  final DateFormat dateFormat;

  @Override
  public synchronized JsonElement serialize(LocalDate date, Type type,
                                            JsonSerializationContext jsonSerializationContext) {
    return Optional.ofNullable(date)
        .flatMap(localDate ->
            Exceptional.of(() ->
                new JsonPrimitive(dateFormat.format(localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())))
                .ifException(e -> Timber.e(e, "Date cannot be serialized, date='%s'", date.toString()))
                .getOptional())
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
