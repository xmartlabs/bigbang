package com.xmartlabs.template.module

import android.content.Context
import com.xmartlabs.template.App
import com.xmartlabs.template.R
import okhttp3.HttpUrl

class RestServiceModule : com.xmartlabs.bigbang.retrofit.module.RestServiceModule() {
  companion object {
    private val BASE_URL = App.context.resources.getString(R.string.base_url)
  }
  
  override fun provideBaseUrl(context: Context?) = HttpUrl.parse(BASE_URL)!!
}
