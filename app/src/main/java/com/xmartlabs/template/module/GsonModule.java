package com.xmartlabs.template.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xmartlabs.template.BuildConfig;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 23/10/15.
 */
@Module
public class GsonModule {
  @Provides
  @Singleton
  public Gson provideGson() {
    GsonBuilder gsonBuilder = new GsonBuilder()
        .registerTypeAdapter(Date.class, new GsonUtcDateAdapter());

    if (BuildConfig.DEBUG) {
      gsonBuilder.setPrettyPrinting();
    }

    return gsonBuilder
        .create();
  }
}
