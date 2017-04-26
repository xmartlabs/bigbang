package com.xmartlabs.bigbang.retrofit.module;

import android.content.Context;

import com.google.gson.Gson;
import com.xmartlabs.bigbang.core.module.OkHttpModule;
import com.xmartlabs.bigbang.retrofit.common.ServiceStringConverter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestServiceModule {
  @Provides
  @Singleton
  Retrofit provideRetrofit(@Named(OkHttpModule.CLIENT_SERVICE) OkHttpClient client,
                           RxJava2CallAdapterFactory rxJavaCallAdapterFactory,
                           GsonConverterFactory gsonConverterFactory,
                           HttpUrl baseUrl,
                           ServiceStringConverter serviceStringConverter) {
    return new Retrofit.Builder()
        .addCallAdapterFactory(rxJavaCallAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .addConverterFactory(serviceStringConverter)
        .baseUrl(baseUrl)
        .client(client)
        .build();
  }

  @Provides
  @Singleton
  RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

  @Provides
  @Singleton
  GsonConverterFactory provideGsonConverterFactory(@Named(ServiceGsonModule.SERVICE_GSON_NAME) Gson gson) {
    return GsonConverterFactory.create(gson);
  }

  @Provides
  @Singleton
  ServiceStringConverter provideStringConverter() {
    return new ServiceStringConverter();
  }

  @Provides
  @Singleton
  protected HttpUrl provideBaseUrl(Context context) {
    throw new UnsupportedOperationException("This method should be overridden in the app module");
  }
}
