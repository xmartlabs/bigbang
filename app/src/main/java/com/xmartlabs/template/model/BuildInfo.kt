package com.xmartlabs.template.model

import com.xmartlabs.template.BuildConfig
import com.xmartlabs.bigbang.core.model.BuildInfo as CoreBuildInfo

object BuildInfo : CoreBuildInfo {
  override val isDebug = BuildConfig.DEBUG
  override val isStaging = BuildConfig.FLAVOR == BuildType.STAGING.toString()
  override val isProduction = BuildConfig.FLAVOR == BuildType.PRODUCTION.toString()
}
