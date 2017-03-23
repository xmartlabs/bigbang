package com.xmartlabs.template.module;

import com.xmartlabs.template.helper.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
  @Provides
  @Singleton
  DatabaseHelper provideDatabaseHelper() {
    return new DatabaseHelper();
  }
}
