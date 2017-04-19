package com.xmartlabs.base.core.module;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xmartlabs.base.core.model.BuildInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {
  @Provides
  @Singleton
  public Gson provideGson(GsonBuilder gsonBuilder) {
    return gsonBuilder.create();
  }

  @NonNull
  @Provides
  public final GsonBuilder provideCommonGsonBuilder(@NonNull BuildInfo buildInfo) {
    return modifyGsonBuilder(new GsonBuilder(), buildInfo);
  }

  protected GsonBuilder modifyGsonBuilder(GsonBuilder builder, @NonNull BuildInfo buildInfo) {
    if (buildInfo.isDebug()) {
      builder.setPrettyPrinting();
    }
    return builder;
  }
}
