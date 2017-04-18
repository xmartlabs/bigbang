package com.xmartlabs.base.retrofit.module;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xmartlabs.base.core.model.BuildInfo;
import com.xmartlabs.base.retrofit.adapter.MillisecondsLocalDateAdapter;
import com.xmartlabs.base.retrofit.adapter.MillisecondsLocalDateTimeAdapter;
import com.xmartlabs.base.retrofit.common.GsonExclude;
import com.xmartlabs.base.retrofit.deserialized.RequiredFieldDeserializer;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

public class GsonModule extends com.xmartlabs.base.core.module.GsonModule {
  @Override
  public Gson provideGson(GsonBuilder gsonBuilder) {
    return gsonBuilder
        .setExclusionStrategies(getExclusionStrategy(null))
        .create();
  }

  @Override
  public Gson provideServiceGson(GsonBuilder gsonBuilder) {
    gsonBuilder
        .setExclusionStrategies(getExclusionStrategy(GsonExclude.Strategy.SERVICE))
        .registerTypeAdapter(LocalDateTime.class, new MillisecondsLocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate.class, new MillisecondsLocalDateAdapter());

    return gsonBuilder.create();
  }

  private ExclusionStrategy getExclusionStrategy(@Nullable GsonExclude.Strategy strategy) {
    return new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return shouldSkipFieldFromSerialization(fieldAttributes)
            || (fieldAttributes.getAnnotation(GsonExclude.class) != null
            && (Objects.equals(fieldAttributes.getAnnotation(GsonExclude.class).strategy(), GsonExclude.Strategy.ALL)
            || Objects.equals(fieldAttributes.getAnnotation(GsonExclude.class).strategy(), strategy)));
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return false;
      }
    };
  }

  protected boolean shouldSkipFieldFromSerialization(@NonNull FieldAttributes fieldAttributes) {
    return false;
  }

  protected GsonBuilder modifyGsonBuilder(@NonNull GsonBuilder builder, @NonNull BuildInfo buildInfo) {
    if (buildInfo.isDebug()) {
      builder.setPrettyPrinting();
    }
    builder.registerTypeHierarchyAdapter(Object.class, new RequiredFieldDeserializer());
    builder.setExclusionStrategies(getExclusionStrategy(null));
    return builder;
  }
}
