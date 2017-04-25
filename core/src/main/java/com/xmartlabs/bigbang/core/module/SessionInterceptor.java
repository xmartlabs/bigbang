package com.xmartlabs.bigbang.core.module;

import android.support.annotation.NonNull;

import com.xmartlabs.bigbang.core.Injector;
import com.xmartlabs.bigbang.core.providers.AccessTokenProvider;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SessionInterceptor implements Interceptor {
  @Inject
  AccessTokenProvider accessTokenProvider;

  public SessionInterceptor() {
    Injector.inject(this);
  }

  @NonNull
  @Override
  public Response intercept(@NonNull Chain chain) throws IOException {
    Request request = accessTokenProvider.provideEntity()
        .map(accessToken -> chain.request()
            .newBuilder()
            .addHeader(accessTokenProvider.provideAccessTokenHeaderKey(), accessToken)
            .build()
        ).orElse(chain.request());
    return chain.proceed(request);
  }
}
