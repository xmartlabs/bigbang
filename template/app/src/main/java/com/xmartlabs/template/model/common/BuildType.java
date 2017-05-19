package com.xmartlabs.template.model.common;

import java.util.Locale;

public enum BuildType {
  STAGING,
  PRODUCTION,
  ;

  @Override
  public String toString() {
    return name().toLowerCase(Locale.getDefault());
  }
}
