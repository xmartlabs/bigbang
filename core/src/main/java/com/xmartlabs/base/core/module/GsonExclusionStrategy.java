package com.xmartlabs.base.core.module;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.xmartlabs.base.core.common.GsonExclude;

import java.util.List;

import lombok.RequiredArgsConstructor;

/** Provides a {@link ExclusionStrategy} that excludes {@link GsonExclude} fields and any other specified class. */
@RequiredArgsConstructor
public class GsonExclusionStrategy {
  private final List<Class<?>> excludedClasses;

  /**
   * Retrieves an {@link ExclusionStrategy} that excludes {@link GsonExclude} fields and the classes contained in
   * {@code excludedClasses}.
   *
   * @param strategy the type of the strategy to be retrieved
   * @return the {@link ExclusionStrategy} for the {@code strategy} provided
   */
  public ExclusionStrategy getExclusionStrategy(@Nullable GsonExclude.Strategy strategy) {
    return new ExclusionStrategy() {
      @Override
      public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return shouldSkipFieldFromSerialization(fieldAttributes)
            || (fieldAttributes.getAnnotation(GsonExclude.class) != null
            && (Objects.equals(fieldAttributes.getAnnotation(GsonExclude.class).strategy(), GsonExclude.Strategy.ALL)
            || Objects.equals(fieldAttributes.getAnnotation(GsonExclude.class).strategy(), strategy)));
      }

      @Override
      public boolean shouldSkipClass(Class<?> clazz) {
        return false;
      }
    };
  }

  /**
   * Returns whether or not the field with {@code fieldAttributes} should be serialized.
   *
   * @param fieldAttributes the attributes of the field to analise
   * @return whether or not the field should be serialized
   */
  protected boolean shouldSkipFieldFromSerialization(@NonNull FieldAttributes fieldAttributes) {
    return Optional.ofNullable(excludedClasses)
        .map(classes -> classes.contains(fieldAttributes.getDeclaredClass()))
        .orElse(false);
  }
}
