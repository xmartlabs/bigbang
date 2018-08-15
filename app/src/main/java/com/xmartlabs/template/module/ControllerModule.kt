package com.xmartlabs.template.module

import com.xmartlabs.bigbang.core.controller.SharedPreferencesSource
import com.xmartlabs.template.controller.SessionRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ControllerModule {
  @Provides
  @Singleton
  internal fun provideSessionController(sharedPreferencesSource: SharedPreferencesSource) = SessionRepository(sharedPreferencesSource)
}
