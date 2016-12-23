package com.xmartlabs.template.module;

import android.support.annotation.NonNull;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.xmartlabs.template.BuildConfig;
import com.xmartlabs.template.common.GsonExclude;
import com.xmartlabs.template.service.adapter.MillisecondsLocalDateAdapter;
import com.xmartlabs.template.service.adapter.MillisecondsLocalDateTimeAdapter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 23/10/15.
 */
@Module
public class GsonModule {
  static final String SERVICE_GSON_NAME = "ServiceGson";

  @Provides
  @Singleton
  Gson provideGson(GsonBuilder gsonBuilder) {
    return gsonBuilder.create();
  }

  @Named(SERVICE_GSON_NAME)
  @Provides
  @Singleton
  @SuppressWarnings("unused")
  Gson provideServiceGson(GsonBuilder gsonBuilder) {
    gsonBuilder
        .registerTypeAdapter(LocalDateTime.class, new MillisecondsLocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate.class, new MillisecondsLocalDateAdapter());

    return gsonBuilder.create();
  }

  @NonNull
  @Provides
  @Singleton
  ExclusionStrategy provideGsonExclusionStrategy() {
    return new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getDeclaredClass().equals(ModelAdapter.class)
            || fieldAttributes.getAnnotation(GsonExclude.class) != null;
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return false;
      }
    };
  }

  @NonNull
  @Provides
  GsonBuilder provideCommonGsonBuilder(ExclusionStrategy exclusionStrategy) {
    GsonBuilder gsonBuilder = new GsonBuilder()
        .setExclusionStrategies(exclusionStrategy);
    if (BuildConfig.DEBUG) {
      gsonBuilder.setPrettyPrinting();
    }
    return gsonBuilder;
  }
}
