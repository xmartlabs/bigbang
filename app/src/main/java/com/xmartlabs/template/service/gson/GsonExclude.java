package com.xmartlabs.template.service.gson;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates that a field should not be serialized/deserialized.
 *
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
