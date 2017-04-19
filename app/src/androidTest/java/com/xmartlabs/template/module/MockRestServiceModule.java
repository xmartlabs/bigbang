package com.xmartlabs.template.module;

import android.content.Context;

import com.xmartlabs.base.retrofit.module.RestServiceModule;

import io.appflate.restmock.RESTMockServer;
import okhttp3.HttpUrl;

public class MockRestServiceModule extends RestServiceModule {
  @Override
  protected HttpUrl provideBaseUrl(Context context) {
    return HttpUrl.parse(RESTMockServer.getUrl());
  }
}
