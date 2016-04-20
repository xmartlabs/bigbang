package com.xmartlabs.template.module;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.xmartlabs.template.BuildConfig;
import com.xmartlabs.template.common.GsonExclude;

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
        .setExclusionStrategies(new ExclusionStrategy() {
          @Override
          public boolean shouldSkipField(FieldAttributes f) {
            return f.getDeclaredClass().equals(ModelAdapter.class)
                || f.getAnnotation(GsonExclude.class) != null;
          }

          @Override
          public boolean shouldSkipClass(Class<?> clazz) {
            return false;
          }
        })
        .registerTypeAdapter(Date.class, new GsonUtcDateAdapter());

    if (BuildConfig.DEBUG) {
      gsonBuilder.setPrettyPrinting();
    }

    return gsonBuilder
        .create();
  }
}
