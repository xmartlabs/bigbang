package com.xmartlabs.bigbang.core.model

/** Defines a set of useful information about the current build.  */
interface BuildInfo {
  /** Checks whether or not the current build is in debug mode. */
  val isDebug: Boolean

  /** Checks whether or not the current environment is staging. */
  val isStaging: Boolean

  /** Checks whether or not the current environment is production. */
  val isProduction: Boolean
}
