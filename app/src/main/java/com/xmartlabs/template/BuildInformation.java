package com.xmartlabs.template;

import com.annimon.stream.Objects;

public class BuildInformation implements com.xmartlabs.base.core.model.BuildInformation {
  @Override
  public boolean isDebug() {
    return BuildConfig.DEBUG;
  }

  @Override
  public boolean isStaging() {
    return Objects.equals(BuildConfig.FLAVOR, BuildType.STAGING);
  }

  @Override
  public boolean isProduction() {
    return Objects.equals(BuildConfig.FLAVOR, BuildType.PRODUCTION);
  }
}
