package com.xmartlabs.template.module;

import android.support.annotation.NonNull;

import com.xmartlabs.bigbang.core.providers.AccessTokenProvider;
import com.xmartlabs.template.service.AuthService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RestServiceModuleAdditions {
  @Provides
  @Singleton
  AuthService provideAuthService(@NonNull Retrofit retrofit) {
    return retrofit.create(AuthService.class);
  }

  @Provides
  @Singleton
  AccessTokenProvider provideAccessTokenProvider() {
    return new AccessTokenProvider();
  }
}
