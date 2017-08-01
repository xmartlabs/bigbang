package com.xmartlabs.bigbang.core.common


/**
 * Annotation to exclude a field from being serialized.
 * Provides a configuration to choose whether to exclude the field of being serialized on:
 *
 *  * Service related operations
 *  * Database related operations
 *  * Both
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class GsonExclude(val strategy: GsonExcludeStrategy = GsonExcludeStrategy.ALL)

enum class GsonExcludeStrategy {
  ALL,
  DATA_BASE,
  SERVICE,
}
