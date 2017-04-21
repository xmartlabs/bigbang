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

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.lang.reflect.Type;

import lombok.RequiredArgsConstructor;
import timber.log.Timber;

/** {@link Gson} type adapter that serializes {@link LocalDate} objects to any specified format. */
@RequiredArgsConstructor
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
  final DateTimeFormatter dateFormat;

  @Override
  public synchronized JsonElement serialize(LocalDateTime dateTime, Type type,
                                            JsonSerializationContext jsonSerializationContext) {
    return Optional.ofNullable(dateTime)
        .flatMap(localDateTime ->
            Exceptional.of(() -> new JsonPrimitive(dateFormat.format(dateTime.atZone(ZoneOffset.UTC).toInstant())))
                .ifException(e -> Timber.e(e, "Date cannot be serialized, date='%s'", localDateTime.toString()))
                .getOptional())
        .orElse(null);
  }

  @Override
  public synchronized LocalDateTime deserialize(JsonElement jsonElement, Type type,
                                                JsonDeserializationContext jsonDeserializationContext) {
    return Optional.ofNullable(jsonElement.getAsString())
        .filter(str -> !StringUtils.isNullOrEmpty(str))
        .flatMap(str ->
            Exceptional.of(() -> ZonedDateTime.parse(str, dateFormat).toLocalDateTime())
                .ifException(e -> Timber.e(e, "Date cannot be parsed, date='%s'", str))
                .getOptional())
        .orElse(null);
  }
}
