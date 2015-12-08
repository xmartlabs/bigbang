package com.scottruth.timeoffandroid.module;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.scottruth.timeoffandroid.helper.DateHelper;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by santiago on 22/09/15.
 */
public class GsonUtcDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    @Override
    public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(DateHelper.DATE_ISO_8601_FORMAT.format(date));
    }

    @Override
    public synchronized Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            return DateHelper.DATE_ISO_8601_FORMAT.parse(jsonElement.getAsString());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }
}
