package com.scottruth.timeoffandroid.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.scottruth.timeoffandroid.TimeOffApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 17/09/15.
 * Copied from: https://github.com/google/dagger/blob/master/examples/android-simple/src/main/java/com/example/dagger/simple/AndroidModule.java
 */
@Module
public class AndroidModule {
    private final TimeOffApplication application;

    public AndroidModule(TimeOffApplication application) {
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
