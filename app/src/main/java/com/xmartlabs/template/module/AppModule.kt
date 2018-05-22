package com.xmartlabs.template.module

import android.app.Application
import com.xmartlabs.bigbang.core.model.BuildInfo as CoreBuildInfo
import com.xmartlabs.template.model.BuildInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
  @Provides
  @Singleton
  fun provideBuildInformation(coreBuildInfo : BuildInfo) : CoreBuildInfo = coreBuildInfo
}
