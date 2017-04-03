package com.xmartlabs.template.module;

import android.util.Log;

import com.xmartlabs.template.helper.GeneralErrorHelper;
import com.xmartlabs.template.log.LoggerTree;
import com.xmartlabs.template.log.logger.CrashlyticsLogger;

import java.util.Arrays;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoggerModule {
  @Provides
  @Singleton
  GeneralErrorHelper provideGeneralErrorHelper() {
    return new GeneralErrorHelper();
  }

  @Provides
  @Singleton
  LoggerTree provideLoggerTree() {
    return LoggerTree.builder()
        .excludedPriorities(Arrays.asList(Log.VERBOSE, Log.DEBUG, Log.INFO))
        .logger(new CrashlyticsLogger())
        .build();
  }
}
