package com.xmartlabs.bigbang.core.model;

/** Defines a set of useful information about the current build. */
public interface BuildInfo {
  /**
   * Checks whether or not the current build is in debug mode.
   *
   * @return true if the build is in debug
   */
  boolean isDebug();

  /**
   * Checks whether or not the current environment is staging.
   *
   * @return true if the environment is staging
   */
  boolean isStaging();

  /**
   * Checks whether or not the current environment is production.
   *
   * @return true if the environment is production
   */
  boolean isProduction();
}
