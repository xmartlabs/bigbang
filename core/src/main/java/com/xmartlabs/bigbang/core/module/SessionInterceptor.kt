package com.xmartlabs.bigbang.core.module

import com.xmartlabs.bigbang.core.providers.AccessTokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class SessionInterceptor @Inject constructor(private val accessTokenProvider: AccessTokenProvider) : Interceptor {

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = accessTokenProvider.provideEntity()
        ?.let {
          chain.request()
              .newBuilder()
              .addHeader(accessTokenProvider.provideAccessTokenHeaderKey(), it)
              .build()
        }
    return chain.proceed(request ?: chain.request())
  }
}
