package com.xmartlabs.template.module;

import com.xmartlabs.template.controller.AuthController;
import com.xmartlabs.template.controller.RepoController;
import com.xmartlabs.template.controller.SessionController;

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
  public AuthController provideAuthController() {
    return new AuthController();
  }

  @Provides
  @Singleton
  public RepoController provideRepoController() {
    return new RepoController();
  }

  @Provides
  @Singleton
  public SessionController provideSessionController() {
    return new SessionController();
  }
}
