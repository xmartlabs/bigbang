package com.xmartlabs.template.module

import com.xmartlabs.bigbang.core.controller.SessionController
import com.xmartlabs.template.controller.AuthController
import com.xmartlabs.template.model.Session
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ControllerModule {
  @Provides
  @Singleton
  internal fun provideAuthController() = AuthController()

  @Provides
  @Singleton
  internal fun provideSessionController() = SessionController(Session::class.java)
}
