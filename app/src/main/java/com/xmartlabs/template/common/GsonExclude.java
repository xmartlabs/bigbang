package com.xmartlabs.template.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to exclude a field from being serialized
 * Provides a configuration to choose whether to exclude the field of being serialized on:
 * <ul>
 *   <li>Service related operations</li>
 *   <li>Database related operations</li>
 *   <li>Both</li>
 * </ul>
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface GsonExclude {
  Strategy strategy() default Strategy.ALL;

  enum Strategy {
    ALL,
    DATA_BASE,
    SERVICE,
  }
}
