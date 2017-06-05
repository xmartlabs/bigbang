package com.xmartlabs.template.module;

import com.xmartlabs.bigbang.core.controller.CoreSessionController;
import com.xmartlabs.template.model.Session;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {
  @Provides
  @Singleton
  CoreSessionController provideSessionController() {
    return new CoreSessionController(Session.class);
  }
}
