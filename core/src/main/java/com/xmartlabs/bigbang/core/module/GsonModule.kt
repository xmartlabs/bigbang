package com.xmartlabs.bigbang.core.module

import com.google.gson.GsonBuilder
import com.xmartlabs.bigbang.core.common.GsonExcludeStrategy
import com.xmartlabs.bigbang.core.model.BuildInfo
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
open class GsonModule {
  @Provides
  @Singleton
  open fun provideGson(gsonBuilder: GsonBuilder) = gsonBuilder
      .setExclusionStrategies(getExclusionStrategy(null))
      .create()

  @Provides
  open fun provideCommonGsonBuilder(buildInfo: BuildInfo) = modifyGsonBuilder(GsonBuilder(), buildInfo)

  protected open fun modifyGsonBuilder(builder: GsonBuilder, buildInfo: BuildInfo) =
    if (buildInfo.isDebug) builder.setPrettyPrinting() else builder

  protected open fun getExclusionStrategy(strategy: GsonExcludeStrategy?) =
      GsonExclusionStrategy(ArrayList()).getExclusionStrategy(strategy)
}
