package com.xmartlabs.bigbang.core.common

/** Used to provide an entity.  */
interface EntityProvider<T> {
  /**
   * Provides the entity.
   * @return the entity
   */
  fun provideEntity(): T?

  /**
   * Updates the entity in the database.
   * @param entity Entity to be saved
   */
  fun updateEntity(entity: T)

  /** Deletes the entity in the database.  */
  fun deleteEntity()
}
