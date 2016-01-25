package com.xmartlabs.template;

/**
 * Created by santiago on 31/08/15.
 */
public enum BuildType {
  STAGING,
  PRODUCTION;

  @Override
  public String toString() {
    switch (this) {
      case STAGING:
        return "staging";
      case PRODUCTION:
        return "production";
      default:
        throw new IllegalStateException("Illegal build type");
    }
  }
}
