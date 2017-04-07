package com.xmartlabs.base.core.module;

import android.util.Log;

import com.xmartlabs.base.core.log.LoggerTree;
import com.xmartlabs.base.core.log.logger.CrashlyticsLogger;

import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoggerModule {
  @Provides
  @Singleton
  LoggerTree provideLoggerTree() {
    return LoggerTree.builder()
        .excludedPriorities(Arrays.asList(Log.VERBOSE, Log.DEBUG, Log.INFO))
        .logger(new CrashlyticsLogger())
        .build();
  }
}
