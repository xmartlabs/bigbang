package com.xmartlabs.template.module;

import android.app.Application;

import com.xmartlabs.base.core.module.CoreAndroidModule;
import com.xmartlabs.template.model.BuildInfo;

public class AndroidModule extends CoreAndroidModule {
  public AndroidModule(Application application) {
    super(application);
  }

  @Override
  public com.xmartlabs.base.core.model.BuildInfo provideBuildInformation() {
    return new BuildInfo();
  }
}
