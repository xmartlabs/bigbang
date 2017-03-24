package com.xmartlabs.template.module;

import android.content.Context;

import dagger.Module;
import io.appflate.restmock.RESTMockServer;

@Module
public class MockRestServiceModule extends RestServiceModule {
  @Override
  protected String getBaseUrl(Context context) {
    return RESTMockServer.getUrl();
  }
}
