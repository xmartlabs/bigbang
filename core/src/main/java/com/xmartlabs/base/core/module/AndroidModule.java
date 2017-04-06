package com.xmartlabs.base.core.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xmartlabs.base.core.model.BuildInformation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {
  private final Application application;

  public AndroidModule(Application application) {
    this.application = application;
  }

  @Provides
  @Singleton
  public Context provideApplicationContext() {
    return application;
  }

  @Provides
  @Singleton
  public BuildInformation provideBuildInformation() {
    return null;
  }

  @Provides
  @Singleton
  public SharedPreferences provideSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }
}
