package com.xmartlabs.template.module;

import com.xmartlabs.template.controller.SessionController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {
  @Provides
  @Singleton
  SessionController provideSessionController() {
    return new SessionController();
  }
}
