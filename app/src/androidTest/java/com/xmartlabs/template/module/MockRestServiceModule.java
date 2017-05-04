package com.xmartlabs.template.module;

import android.content.Context;

import com.xmartlabs.bigbang.retrofit.module.RestServiceModule;

import io.appflate.restmock.RESTMockServer;
import okhttp3.HttpUrl;

public class MockRestServiceModule extends RestServiceModule {
  @Override
  public HttpUrl provideBaseUrl(Context context) {
    return HttpUrl.parse(RESTMockServer.getUrl());
  }
}
