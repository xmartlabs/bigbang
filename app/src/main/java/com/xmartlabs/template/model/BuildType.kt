package com.xmartlabs.template.model

enum class BuildType {
  STAGING,
  PRODUCTION,
  ;

  override fun toString() = when (this) {
    STAGING -> "staging"
    PRODUCTION -> "production"
  }
}
