package com.xmartlabs.template.module;

import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.controller.demo.DemoController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 31/08/15.
 */
@Module
public class ControllerModule {
  @Provides
  @Singleton
  public SessionController provideSessionController() {
    return new SessionController();
  }

  @Provides
  @Singleton
  public DemoController provideDemoController() {
    return new DemoController();
  }
}
