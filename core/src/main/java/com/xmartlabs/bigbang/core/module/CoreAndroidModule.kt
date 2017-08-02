package com.xmartlabs.bigbang.core.module

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.xmartlabs.bigbang.core.model.BuildInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class CoreAndroidModule(private val application: Application) {
  @Provides
  @Singleton
  fun provideApplicationContext(): Context = application

  @Provides
  @Singleton
  open fun provideBuildInformation(): BuildInfo =
      throw IllegalStateException("A BuildInfo concrete class must be provided")

  @Provides
  @Singleton
  fun provideSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(application)
}
