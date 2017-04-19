package com.xmartlabs.base.core.module;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Objects;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.xmartlabs.base.core.common.GsonExclude;

public abstract class GsonExclusionStrategy {
  protected ExclusionStrategy getExclusionStrategy(@Nullable GsonExclude.Strategy strategy) {
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

  protected boolean shouldSkipFieldFromSerialization(@NonNull FieldAttributes fieldAttributes) {
    return false;
  }
}
