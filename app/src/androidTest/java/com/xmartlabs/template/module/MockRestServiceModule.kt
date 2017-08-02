package com.xmartlabs.template.module

import android.content.Context

import com.xmartlabs.bigbang.retrofit.module.RestServiceModule

import io.appflate.restmock.RESTMockServer
import okhttp3.HttpUrl

class MockRestServiceModule : RestServiceModule() {
  override fun provideBaseUrl(context: Context) = HttpUrl.parse(RESTMockServer.getUrl())
}
