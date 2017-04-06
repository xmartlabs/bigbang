package com.xmartlabs.template.module;

import android.app.Application;

import com.xmartlabs.template.BuildInformation;

public class AndroidModule extends com.xmartlabs.base.core.module.AndroidModule {
  public AndroidModule(Application application) {
    super(application);
  }

  @Override
  public com.xmartlabs.base.core.model.BuildInformation provideBuildInformation() {
    return new BuildInformation();
  }
}
