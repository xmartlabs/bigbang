package com.xmartlabs.bigbang.retrofit.module

import android.content.Context
import com.google.gson.Gson
import com.xmartlabs.bigbang.core.module.OkHttpModule
import com.xmartlabs.bigbang.retrofit.common.ServiceStringConverter
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
open class RestServiceModule {
  @Provides
  @Singleton
  fun provideRetrofit(@Named(OkHttpModule.CLIENT_SERVICE) client: OkHttpClient,
                      rxJavaCallAdapterFactory: RxJava2CallAdapterFactory,
                      gsonConverterFactory: GsonConverterFactory,
                      baseUrl: HttpUrl,
                      serviceStringConverter: ServiceStringConverter): Retrofit {
    return Retrofit.Builder()
        .addCallAdapterFactory(rxJavaCallAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .addConverterFactory(serviceStringConverter)
        .baseUrl(baseUrl)
        .client(client)
        .build()
  }

  @Provides
  @Singleton
  fun provideRxJavaCallAdapterFactory() = RxJava2CallAdapterFactory.create()

  @Provides
  @Singleton
  fun provideGsonConverterFactory(@Named(ServiceGsonModule.SERVICE_GSON_NAME) gson: Gson) =
      GsonConverterFactory.create(gson)

  @Provides
  @Singleton
  fun provideStringConverter() = ServiceStringConverter()

  @Provides
  @Singleton
  open fun provideBaseUrl(context: Context): HttpUrl {
    throw UnsupportedOperationException("This method should be overridden in the app module")
  }
}
