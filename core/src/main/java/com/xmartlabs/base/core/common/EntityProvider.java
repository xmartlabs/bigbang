package com.xmartlabs.base.core.common;

import android.support.annotation.Nullable;

import com.annimon.stream.Optional;

/** Used to provide an entity. */
public interface EntityProvider<T> {
  /**
   * Provides the entity.
   *
   * @return the entity
   */
  Optional<T> provideEntity();

  /**
   * Updates the entity in the database.
   *
   * @param entity Entity to be saved
   */
  void updateEntity(@Nullable T entity);

  /** Deletes the entity in the database. */
  void deleteEntity();
}
