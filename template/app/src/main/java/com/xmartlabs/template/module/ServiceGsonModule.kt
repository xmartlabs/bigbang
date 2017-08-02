package com.xmartlabs.template.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.xmartlabs.bigbang.core.model.BuildInfo
import com.xmartlabs.bigbang.retrofit.module.ServiceGsonModule as CoreServiceGsonModule

class ServiceGsonModule : CoreServiceGsonModule() {
  override fun modifyGsonBuilder(builder: GsonBuilder, buildInfo: BuildInfo): GsonBuilder {
    if (buildInfo.isDebug) {
      builder.setPrettyPrinting()
    }
    builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    return builder
  }
}
