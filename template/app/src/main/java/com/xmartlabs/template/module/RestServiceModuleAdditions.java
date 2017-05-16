package com.xmartlabs.template.module;

import android.support.annotation.NonNull;

import com.xmartlabs.bigbang.core.providers.AccessTokenProvider;
import com.xmartlabs.bigbang.retrofit.common.ServiceStringConverter;
import com.xmartlabs.template.R;
import com.xmartlabs.template.TemplateApplication;
import com.xmartlabs.template.service.AuthService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestServiceModuleAdditions {
  private static final String BASE_URL = TemplateApplication.getContext().getString(R.string.base_url);
  private static final String ADDITIONS_RETROFIT_NAME = "AdditionsRetrofit";

  @Provides
  @Singleton
  AuthService provideAuthService(@NonNull @Named(ADDITIONS_RETROFIT_NAME) Retrofit retrofit) {
    return retrofit.create(AuthService.class);
  }

  @Provides
  @Singleton
  AccessTokenProvider provideAccessTokenProvider() {
    return new AccessTokenProvider();
  }

  @Named(ADDITIONS_RETROFIT_NAME)
  @Provides
  @Singleton
  public Retrofit provideOauthRetrofit(@Named(OkHttpModule.CLIENT_SERVICE) OkHttpClient client,
                                       RxJava2CallAdapterFactory rxJavaCallAdapterFactory,
                                       GsonConverterFactory gsonConverterFactory,
                                       ServiceStringConverter serviceStringConverter) {
    return new Retrofit.Builder()
        .addCallAdapterFactory(rxJavaCallAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .addConverterFactory(serviceStringConverter)
        .baseUrl(BASE_URL)
        .client(client)
        .build();
  }
}
