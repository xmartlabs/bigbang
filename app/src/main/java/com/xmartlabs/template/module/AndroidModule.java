package com.xmartlabs.template.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xmartlabs.template.BaseProjectApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 17/09/15.
 * Copied from: https://github.com/google/dagger/blob/master/examples/android-simple/src/main/java/com/example/dagger/simple/AndroidModule.java
 */
@Module
public class AndroidModule {
  private final BaseProjectApplication application;

  public AndroidModule(BaseProjectApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  //@ForApplication // FIXME: doesn't work with this
  public Context provideApplicationContext() {
    return application;
  }

  @Provides
  @Singleton
  public SharedPreferences provideSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }
}
