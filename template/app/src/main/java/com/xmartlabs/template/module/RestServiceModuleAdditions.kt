package com.xmartlabs.template.module

import com.xmartlabs.bigbang.core.providers.AccessTokenProvider
import com.xmartlabs.template.service.AuthService

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RestServiceModuleAdditions {
  @Provides
  @Singleton
  internal fun provideAuthService(retrofit: Retrofit) = retrofit.create(AuthService::class.java)

  @Provides
  @Singleton
  internal fun provideAccessTokenProvider() = AccessTokenProvider()
}
