package com.xmartlabs.base.ui.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mike on 10/04/2017.
 */
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
}

