package com.xmartlabs.template.service.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.xmartlabs.template.helper.StringUtils;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.lang.reflect.Type;

import timber.log.Timber;

/**
 * Created by mirland on 24/08/16.
 */
public class EpochSecondsLocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  @Override
  public synchronized JsonElement serialize(LocalDate date, Type type,
                                            JsonSerializationContext jsonSerializationContext) {
    if (date != null) {
      return new JsonPrimitive(date.atStartOfDay(ZoneOffset.UTC).toEpochSecond());
    }
    return null;
  }

  @Override
  public synchronized LocalDate deserialize(JsonElement jsonElement, Type type,
                                            JsonDeserializationContext jsonDeserializationContext) {
    String dateAsString = jsonElement.getAsString();
    if (!StringUtils.stringIsNullOrEmpty(dateAsString)) {
      try {
        return LocalDateTime.ofEpochSecond(Long.valueOf(dateAsString), 0, ZoneOffset.UTC).toLocalDate();
      } catch (Exception e) {
        Timber.e(e, "Date cannot be parsed, date='%s'", dateAsString);
      }
    }
    return null;
  }
}
