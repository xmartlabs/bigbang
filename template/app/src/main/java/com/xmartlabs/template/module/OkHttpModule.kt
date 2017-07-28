package com.xmartlabs.template.module

import com.xmartlabs.bigbang.core.model.BuildInfo
import com.xmartlabs.bigbang.core.module.SessionInterceptor

import okhttp3.OkHttpClient

class OkHttpModule : com.xmartlabs.bigbang.core.module.OkHttpModule() {
  override fun provideServiceOkHttpClient(clientBuilder: OkHttpClient.Builder, buildInfo: BuildInfo): OkHttpClient {
    clientBuilder.addInterceptor(SessionInterceptor())
    return super.provideServiceOkHttpClient(clientBuilder, buildInfo)
  }
}
