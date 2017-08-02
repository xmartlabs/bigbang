package com.xmartlabs.bigbang.core

import bullet.ObjectGraph

class Injector private constructor() {
  var bullet: ObjectGraph? = null

  companion object {
    @JvmStatic
    val instance by lazy { Injector() }

    @JvmStatic
    fun <T> inject(t: T) {
      if (instance.bullet == null) {
        throw IllegalStateException("No ObjectGraph is present. Did you forgot to call setObjectGraph?")
      }
      instance.bullet?.inject(t)
    }
  }
}
