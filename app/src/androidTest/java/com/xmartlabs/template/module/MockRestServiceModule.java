package com.xmartlabs.template.module;

import android.content.Context;

import dagger.Module;
import io.appflate.restmock.RESTMockServer;

/**
 * Created by medina on 21/09/2016.
 */
@Module
public class MockRestServiceModule extends RestServiceModule {
  @Override
  protected String getBaseUrl(Context context) {
    return RESTMockServer.getUrl();
  }
}
