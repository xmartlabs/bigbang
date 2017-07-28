package com.xmartlabs.template.model.common

import com.xmartlabs.template.BuildConfig

class BuildInfo : com.xmartlabs.bigbang.core.model.BuildInfo {
  override fun isDebug() = BuildConfig.DEBUG

  override fun isStaging() = BuildConfig.FLAVOR == BuildType.STAGING.toString()

  override fun isProduction() = BuildConfig.FLAVOR == BuildType.PRODUCTION.toString()
}
