package com.xmartlabs.template.model.common

import com.xmartlabs.template.BuildConfig
import com.xmartlabs.bigbang.core.model.BuildInfo as CoreBuildInfo

class BuildInfo : CoreBuildInfo {
  override val isDebug = BuildConfig.DEBUG
  override val isProduction: Boolean
    get() = BuildConfig.FLAVOR == BuildType.STAGING.toString()
  override val isStaging: Boolean
    get() = BuildConfig.FLAVOR == BuildType.PRODUCTION.toString()
}
