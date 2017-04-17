package com.xmartlabs.base.core.module;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.xmartlabs.base.core.model.BuildInfo;
import com.xmartlabs.base.core.service.adapter.MillisecondsLocalDateAdapter;
import com.xmartlabs.base.core.service.adapter.MillisecondsLocalDateTimeAdapter;
import com.xmartlabs.base.core.service.common.GsonExclude;
import com.xmartlabs.base.core.service.deserializer.RequiredFieldDeserializer;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {
  static final String SERVICE_GSON_NAME = "ServiceGson";

  @Provides
  @Singleton
  public Gson provideGson(GsonBuilder gsonBuilder) {
    return gsonBuilder
        .setExclusionStrategies(getExclusionStrategy(null))
        .create();
  }

  @Named(SERVICE_GSON_NAME)
  @Provides
  @Singleton
  @SuppressWarnings("unused")
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
        return fieldAttributes.getDeclaredClass().equals(ModelAdapter.class)
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

  @NonNull
  @Provides
  public GsonBuilder provideCommonGsonBuilder(@NonNull BuildInfo buildInfo) {
    GsonBuilder gsonBuilder = modifyGsonBuilder(new GsonBuilder(), buildInfo);
    gsonBuilder.registerTypeHierarchyAdapter(Object.class, new RequiredFieldDeserializer());
    return gsonBuilder;
  }

  protected GsonBuilder modifyGsonBuilder(GsonBuilder builder, @NonNull BuildInfo buildInfo) {
    if (buildInfo.isDebug()) {
      builder.setPrettyPrinting();
    }
    return builder;
  }
}
