package com.xmartlabs.template.model.common;

public enum BuildType {
  STAGING,
  PRODUCTION,
  ;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
