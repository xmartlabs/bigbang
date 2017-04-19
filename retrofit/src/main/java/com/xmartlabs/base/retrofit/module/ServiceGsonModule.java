package com.xmartlabs.base.retrofit.module;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xmartlabs.base.core.model.BuildInfo;
import com.xmartlabs.base.core.adapter.MillisecondsLocalDateAdapter;
import com.xmartlabs.base.core.adapter.MillisecondsLocalDateTimeAdapter;
import com.xmartlabs.base.core.common.GsonExclude;
import com.xmartlabs.base.core.module.GsonExclusionStrategy;
import com.xmartlabs.base.retrofit.deserialized.RequiredFieldDeserializer;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceGsonModule extends GsonExclusionStrategy {
  public static final String SERVICE_GSON_NAME = "ServiceGson";

  @Named(SERVICE_GSON_NAME)
  @Provides
  @Singleton
  @SuppressWarnings("unused")
  public Gson provideServiceGson(@NonNull GsonBuilder gsonBuilder, @NonNull BuildInfo buildInfo) {
    return modifyGsonBuilder(gsonBuilder, buildInfo)
        .setExclusionStrategies(getExclusionStrategy(GsonExclude.Strategy.SERVICE))
        .create();
  }

  protected GsonBuilder modifyGsonBuilder(@NonNull GsonBuilder builder, @NonNull BuildInfo buildInfo) {
    if (buildInfo.isDebug()) {
      builder.setPrettyPrinting();
    }
    builder.registerTypeHierarchyAdapter(Object.class, new RequiredFieldDeserializer());
    return builder;
  }
}
