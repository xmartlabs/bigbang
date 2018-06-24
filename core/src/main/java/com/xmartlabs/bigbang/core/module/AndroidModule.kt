package com.xmartlabs.bigbang.core.module

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.xmartlabs.bigbang.core.model.BuildInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule {
  @Provides
  @Singleton
  fun provideApplicationContext(application: Application): Context = application

  @Provides
  @Singleton
  fun provideSharedPreferences(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
}
