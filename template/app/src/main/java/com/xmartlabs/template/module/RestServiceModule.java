package com.xmartlabs.template.module;

import android.content.Context;

import com.xmartlabs.template.R;
import com.xmartlabs.template.TemplateApplication;

import okhttp3.HttpUrl;

public class RestServiceModule extends com.xmartlabs.bigbang.retrofit.module.RestServiceModule {
  private static final String BASE_URL = TemplateApplication.getContext().getResources().getString(R.string.base_url);

  @Override
  public HttpUrl provideBaseUrl(Context context) {
    return HttpUrl.parse(BASE_URL);
  }
}
