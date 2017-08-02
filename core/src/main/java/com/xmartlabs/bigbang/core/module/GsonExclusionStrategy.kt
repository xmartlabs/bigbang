package com.xmartlabs.bigbang.core.module

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.xmartlabs.bigbang.core.common.GsonExclude
import com.xmartlabs.bigbang.core.common.GsonExcludeStrategy

/** Provides a [ExclusionStrategy] that excludes [GsonExclude] fields and any other specified class.  */
open class GsonExclusionStrategy(val excludedClasses: List<Class<Any>>? = null) {
  /**
   * Retrieves an [ExclusionStrategy] that excludes [GsonExclude] fields and the classes contained in
   * `excludedClasses`.
   * @param strategy the type of the strategy to be retrieved
   * *
   * @return the [ExclusionStrategy] for the `strategy` provided
   */
  open fun getExclusionStrategy(strategy: GsonExcludeStrategy?) = object : ExclusionStrategy {
    override fun shouldSkipField(fieldAttributes: FieldAttributes) =
        shouldSkipFieldFromSerialization(fieldAttributes)
            || fieldAttributes.getAnnotation(GsonExclude::class.java) != null
            && (fieldAttributes.getAnnotation(GsonExclude::class.java).strategy == GsonExcludeStrategy.ALL
            || fieldAttributes.getAnnotation(GsonExclude::class.java).strategy == strategy)
  
    override fun shouldSkipClass(clazz: Class<*>) = false
  }

  /**
   * Returns whether or not the field with `fieldAttributes` should be serialized.
   *
   * @param fieldAttributes the attributes of the field to analise
   * *
   * @return whether or not the field should be serialized
   */
  protected open fun shouldSkipFieldFromSerialization(fieldAttributes: FieldAttributes) =
      excludedClasses?.contains(fieldAttributes.declaredClass) ?: false
}
