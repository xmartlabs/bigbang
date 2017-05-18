package com.xmartlabs.template.module;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.xmartlabs.bigbang.core.model.BuildInfo;

public class ServiceGsonModule extends com.xmartlabs.bigbang.retrofit.module.ServiceGsonModule {
  @Override
  protected GsonBuilder modifyGsonBuilder(@NonNull GsonBuilder builder, @NonNull BuildInfo buildInfo) {
    if (buildInfo.isDebug()) {
      builder.setPrettyPrinting();
    }
    builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    return builder;
  }
}
