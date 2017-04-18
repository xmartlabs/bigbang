package com.xmartlabs.base.core.module;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xmartlabs.base.core.model.BuildInfo;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {
  public static final String SERVICE_GSON_NAME = "ServiceGson";

  @Provides
  @Singleton
  public Gson provideGson(GsonBuilder gsonBuilder) {
    return gsonBuilder.create();
  }

  @Named(SERVICE_GSON_NAME)
  @Provides
  @Singleton
  @SuppressWarnings("unused")
  public Gson provideServiceGson(GsonBuilder gsonBuilder) {
    return gsonBuilder.create();
  }

  @NonNull
  @Provides
  public final GsonBuilder provideCommonGsonBuilder(@NonNull BuildInfo buildInfo) {
    return modifyGsonBuilder(modifyGsonBuilder(new GsonBuilder(), buildInfo), buildInfo);
  }

  protected GsonBuilder modifyGsonBuilder(GsonBuilder builder, @NonNull BuildInfo buildInfo) {
    if (buildInfo.isDebug()) {
      builder.setPrettyPrinting();
    }
    return builder;
  }
}
