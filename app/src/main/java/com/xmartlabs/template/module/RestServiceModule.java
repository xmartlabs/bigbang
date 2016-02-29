package com.xmartlabs.template.module;

import android.content.Context;

import com.google.gson.Gson;
import com.xmartlabs.template.R;
import com.xmartlabs.template.service.AuthService;
import com.xmartlabs.template.service.RepoService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by santiago on 31/08/15.
 */
@Module
public class RestServiceModule {
  @Provides
  @Singleton
  public Retrofit provideRetrofit(Context context, @Named(OkHttpModule.CLIENT_SERVICE) OkHttpClient client,
                                  RxJavaCallAdapterFactory rxJavaCallAdapterFactory,
                                  GsonConverterFactory gsonConverterFactory) {
    return new Retrofit.Builder()
        .addCallAdapterFactory(rxJavaCallAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .baseUrl(context.getString(R.string.url_service))
        .client(client)
        .build();
  }

  @Provides
  @Singleton
  public RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
    return RxJavaCallAdapterFactory.create();
  }

  @Provides
  @Singleton
  public GsonConverterFactory provideGsonConverterFactory(Gson gson) {
    return GsonConverterFactory.create(gson);
  }

  @Provides
  @Singleton
  public AuthService provideAuthService(Retrofit retrofit) {
    return retrofit.create(AuthService.class);
  }

  // TODO: Just for demo, delete this method in the real project
  @Provides
  @Singleton
  public RepoService provideRepoService(Retrofit retrofit) {
    return retrofit.create(RepoService.class);
  }
}
