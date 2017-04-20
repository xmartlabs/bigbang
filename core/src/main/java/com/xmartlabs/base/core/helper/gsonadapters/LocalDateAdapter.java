package com.xmartlabs.base.core.helper.gsonadapters;

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
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.text.DateFormat;

import lombok.RequiredArgsConstructor;
import timber.log.Timber;

<<<<<<< HEAD:core/src/main/java/com/xmartlabs/base/core/adapter/MillisecondsLocalDateAdapter.java
/** {@link Gson} type adapter that serializes {@link LocalDate} objects to a millisecond format. */
public class MillisecondsLocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  @Nullable
=======
/** {@link Gson} type adapter that serializes {@link LocalDate} objects to any specified format. */
@RequiredArgsConstructor
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  final DateFormat dateFormat;

>>>>>>> 9df95cb... generifying Date time adapters:core/src/main/java/com/xmartlabs/base/core/helper/gsonadapters/LocalDateAdapter.java
  @Override
  public synchronized JsonElement serialize(@Nullable LocalDate date, @Nullable Type type,
                                            @Nullable JsonSerializationContext jsonSerializationContext) {
    return Optional.ofNullable(date)
        .flatMap(localDate ->
            Exceptional.of(() ->
                new JsonPrimitive(dateFormat.format(localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())))
                .ifException(e -> Timber.e(e, "Date cannot be serialized, date='%s'", date.toString()))
                .getOptional())
        .orElse(null);
  }

  @Nullable
  public synchronized JsonElement serialize(LocalDate date) {
    return serialize(date, null, null);
  }

  @Nullable
  @Override
  public synchronized LocalDate deserialize(@Nullable JsonElement jsonElement, @Nullable Type type,
                                            @Nullable JsonDeserializationContext jsonDeserializationContext) {
    return Optional.ofNullable(jsonElement)
        .map(JsonElement::getAsString)
        .filter(str -> !StringUtils.isNullOrEmpty(str))
        .flatMap(str ->
            Exceptional.of(() -> Instant.ofEpochMilli(Long.valueOf(str)).atZone(ZoneOffset.UTC).toLocalDate())
            .ifException(e -> Timber.e(e, "Date cannot be parsed, date='%s'", str))
            .getOptional())
        .orElse(null);
  }

  @Nullable
  public synchronized LocalDate deserialize(@Nullable JsonElement jsonElement) {
    return deserialize(jsonElement, null, null);
  }
}
