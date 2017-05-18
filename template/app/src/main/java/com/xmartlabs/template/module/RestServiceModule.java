package com.xmartlabs.template.module;

import android.content.Context;

import com.xmartlabs.bigbang.core.providers.AccessTokenProvider;
import com.xmartlabs.bigbang.retrofit.common.ServiceStringConverter;
import com.xmartlabs.template.TemplateApplication;
import com.xmartlabs.template.R;
import com.xmartlabs.template.service.AuthService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestServiceModule extends com.xmartlabs.bigbang.retrofit.module.RestServiceModule {
  private static final String BASE_URL = TemplateApplication.getContext().getResources().getString(R.string.base_url);

  @Override
  public HttpUrl provideBaseUrl(Context context) {
    return HttpUrl.parse(BASE_URL);
  }
}
