package com.xmartlabs.base.retrofit.deserialized;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.xmartlabs.base.retrofit.common.GsonRequired;
import com.xmartlabs.base.retrofit.exception.JsonRequiredFieldException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Locale;

import timber.log.Timber;

/** {@link JsonDeserializer} that checks that all {@link GsonRequired} fields have a value. */
public class RequiredFieldDeserializer implements JsonDeserializer<Object> {
  private Gson gson = new Gson();

  @Override
  public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    Object instance = gson.fromJson(json, typeOfT);

    for (Field field : instance.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      if (field.getAnnotation(GsonRequired.class) != null) {
        try {
          if (field.get(instance) == null) {
            String message = String.format(Locale.US, "The field %s is required", field.getName());
            throw new JsonRequiredFieldException(message);
          }
        } catch (IllegalAccessException exception) {
          Timber.e(exception);
        }
      }
      if (!field.getType().isPrimitive() && !field.getType().equals(String.class)) {
        try {
          Object object = field.get(instance);
          if (object != null) {
            deserialize(gson.toJsonTree(object), field.getType(), context);
          }
        } catch (IllegalAccessException exception) {
          Timber.e(exception);
        }
      }
    }

    return instance;
  }
}
