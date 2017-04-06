package com.xmartlabs.template.module;

import android.support.annotation.NonNull;

import com.xmartlabs.template.providers.AccessTokenProvider;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SessionInterceptor implements Interceptor {
  @NonNull
  private final AccessTokenProvider accessTokenProvider;

  @Inject
  public SessionInterceptor(@NonNull AccessTokenProvider accessTokenProvider) {
    this.accessTokenProvider = accessTokenProvider;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = accessTokenProvider.provideEntity()
        .map(accessToken -> chain.request()
            .newBuilder()
            .addHeader("session", accessToken)
            .build()
        ).orElse(chain.request());
    return chain.proceed(request);
  }
}
