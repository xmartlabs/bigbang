package com.xmartlabs.template.module

import com.xmartlabs.bigbang.core.controller.CoreSessionController
import com.xmartlabs.template.controller.AuthController
import com.xmartlabs.template.controller.SessionController
import com.xmartlabs.template.model.Session

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ControllerModule {
  @Provides
  @Singleton
  internal fun provideAuthController() = AuthController()

  @Provides
  @Singleton
  internal fun provideCoreSessionController(sessionController: SessionController): CoreSessionController {
    return sessionController
  }

  @Provides
  @Singleton
  internal fun provideSessionController() = SessionController(Session::class.java)
}
