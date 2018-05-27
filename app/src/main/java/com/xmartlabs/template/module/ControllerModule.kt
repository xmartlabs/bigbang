package com.xmartlabs.template.module

import com.xmartlabs.bigbang.core.controller.SharedPreferencesController
import com.xmartlabs.template.controller.SessionController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ControllerModule {
  @Provides
  @Singleton
  internal fun provideSessionController(sharedPreferencesController: SharedPreferencesController) = SessionController(sharedPreferencesController)
}
