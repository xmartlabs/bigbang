package com.xmartlabs.template.module;

import android.app.Application;

import com.xmartlabs.bigbang.core.module.CoreAndroidModule;
import com.xmartlabs.template.model.common.BuildInfo;

public class AndroidModule extends CoreAndroidModule {
  public AndroidModule(Application application) {
    super(application);
  }

  @Override
  public com.xmartlabs.bigbang.core.model.BuildInfo provideBuildInformation() {
    return new BuildInfo();
  }
}
