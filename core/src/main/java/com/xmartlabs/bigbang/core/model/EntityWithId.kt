package com.xmartlabs.bigbang.core.model

/** Defines an entity with a unique Id of type [T] */
interface EntityWithId<out T> {
  val id: T
}
