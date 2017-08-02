package com.xmartlabs.bigbang.core.module

import com.xmartlabs.bigbang.core.Injector
import com.xmartlabs.bigbang.core.providers.AccessTokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class SessionInterceptor : Interceptor {
  @Inject
  internal lateinit var accessTokenProvider: AccessTokenProvider

  init {
    Injector.inject<Any>(this)
  }

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = accessTokenProvider.provideEntity()
        ?.let { chain.request()
            .newBuilder()
            .addHeader(accessTokenProvider.provideAccessTokenHeaderKey(), it)
            .build()
        }
    return chain.proceed(request ?: chain.request())
  }
}
