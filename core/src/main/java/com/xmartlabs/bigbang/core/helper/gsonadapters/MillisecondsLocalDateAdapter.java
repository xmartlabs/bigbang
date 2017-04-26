package com.xmartlabs.bigbang.core.helper.gsonadapters;

import android.support.annotation.Nullable;

import com.annimon.stream.Exceptional;
import com.annimon.stream.Optional;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.xmartlabs.bigbang.core.helper.StringUtils;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneOffset;

import java.lang.reflect.Type;

import timber.log.Timber;

public class MillisecondsLocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  @Nullable
  @Override
  public synchronized JsonElement serialize(@Nullable LocalDate date, @Nullable Type type,
                                            @Nullable JsonSerializationContext jsonSerializationContext) {
    return Optional.ofNullable(date)
        .flatMap(theDate ->
            Exceptional.of(() -> new JsonPrimitive(theDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()))
                .ifException(e -> Timber.e(e, "Date cannot be serialized, date='%s'", theDate.toString()))
                .getOptional()
        )
        .orElse(null);
  }

  @Nullable
  @Override
  public synchronized LocalDate deserialize(@Nullable JsonElement jsonElement, @Nullable Type type,
                                            @Nullable JsonDeserializationContext jsonDeserializationContext) {
      return Optional.ofNullable(jsonElement)
          .map(JsonElement::toString)
          .filter(str -> !StringUtils.isNullOrEmpty(str))
          .flatMap(str -> Exceptional.of(() -> Instant.ofEpochMilli(Long.valueOf(str)).atZone(ZoneOffset.UTC).toLocalDate())
              .ifException(e -> Timber.e(e, "Date cannot be parsed, date='%s'", str))
              .getOptional())
          .orElse(null);
  }
}
