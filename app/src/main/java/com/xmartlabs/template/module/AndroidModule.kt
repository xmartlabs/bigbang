package com.xmartlabs.template.module

import android.app.Application

import com.xmartlabs.bigbang.core.module.CoreAndroidModule
import com.xmartlabs.template.model.BuildInfo
import com.xmartlabs.bigbang.core.model.BuildInfo as CoreBuildInfo

class AndroidModule(application: Application) : CoreAndroidModule(application) {
  override fun provideBuildInformation(): CoreBuildInfo = BuildInfo()
}
