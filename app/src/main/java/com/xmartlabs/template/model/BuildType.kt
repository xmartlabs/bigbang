package com.xmartlabs.template.model

enum class BuildType {
  STAGING,
  PRODUCTION;

  override fun toString(): String {
    when (this) {
      STAGING -> return "staging"
      PRODUCTION -> return "production"
      else -> throw IllegalStateException("Illegal build type")
    }
  }
}
