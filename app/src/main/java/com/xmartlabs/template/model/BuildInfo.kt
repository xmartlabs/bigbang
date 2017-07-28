package com.xmartlabs.template.model

import com.xmartlabs.template.BuildConfig

class BuildInfo : com.xmartlabs.bigbang.core.model.BuildInfo {
  override val isDebug: Boolean
    get() = BuildConfig.DEBUG

  override val isStaging: Boolean
    get() = BuildConfig.FLAVOR == BuildType.STAGING.toString()

  override val isProduction: Boolean
    get() = BuildConfig.FLAVOR == BuildType.PRODUCTION.toString()
}
