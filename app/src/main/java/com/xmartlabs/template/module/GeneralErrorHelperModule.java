package com.xmartlabs.template.module;

import com.xmartlabs.template.helper.GeneralErrorHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 12/10/15.
 */
@Module
public class GeneralErrorHelperModule {
  private static GeneralErrorHelper generalErrorHelper; // To get the GeneralErrorHelper in Application.OnCreate.

  @Provides
  @Singleton
  public GeneralErrorHelper provideGeneralErrorHelper() {
    if (generalErrorHelper == null) {
      generalErrorHelper = new GeneralErrorHelper();
    }
    return generalErrorHelper;
  }
}
