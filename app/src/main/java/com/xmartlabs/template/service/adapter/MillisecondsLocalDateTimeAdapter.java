package com.xmartlabs.template.service.adapter;

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
 * Created by santiago on 06/09/16.
 */
public class MillisecondsLocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
  @Override
  public synchronized JsonElement serialize(LocalDateTime dateTime, Type type,
                                            JsonSerializationContext jsonSerializationContext) {
    if (dateTime != null) {
      return new JsonPrimitive(dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
    }
    return null;
  }

  @Override
  public synchronized LocalDateTime deserialize(JsonElement jsonElement, Type type,
                                                JsonDeserializationContext jsonDeserializationContext) {
    String dateTimeAsString = jsonElement.getAsString();
    if (!StringUtils.stringIsNullOrEmpty(dateTimeAsString)) {
      try {
        return Instant.ofEpochMilli(Long.valueOf(dateTimeAsString)).atZone(ZoneOffset.UTC).toLocalDateTime();
      } catch (Exception e) {
        Timber.e(e, "DateTime cannot be parsed, dateTime='%s'", dateTimeAsString);
      }
    }
    return null;
  }
}
