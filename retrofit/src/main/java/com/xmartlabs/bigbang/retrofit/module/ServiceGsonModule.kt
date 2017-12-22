package com.xmartlabs.bigbang.retrofit.module

import com.google.gson.ExclusionStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xmartlabs.bigbang.core.common.GsonExcludeStrategy
import com.xmartlabs.bigbang.core.model.BuildInfo
import com.xmartlabs.bigbang.core.module.GsonExclusionStrategy
import dagger.Module
import dagger.Provides
import java.util.ArrayList
import javax.inject.Named
import javax.inject.Singleton

@Module
open class ServiceGsonModule {
  companion object {
    const val SERVICE_GSON_NAME = "ServiceGson"
  }
  
  @Named(SERVICE_GSON_NAME)
  @Provides
  @Singleton
  open fun provideServiceGson(gsonBuilder: GsonBuilder, buildInfo: BuildInfo): Gson {
    return modifyGsonBuilder(gsonBuilder, buildInfo)
        .setExclusionStrategies(getExclusionStrategy(GsonExcludeStrategy.SERVICE))
        .create()
  }

  protected open fun modifyGsonBuilder(builder: GsonBuilder, buildInfo: BuildInfo): GsonBuilder {
    if (buildInfo.isDebug) {
      builder.setPrettyPrinting()
    }
    return builder
  }

  protected open fun getExclusionStrategy(strategy: GsonExcludeStrategy?): ExclusionStrategy {
    return GsonExclusionStrategy(ArrayList())
        .getExclusionStrategy(strategy)
  }
}
