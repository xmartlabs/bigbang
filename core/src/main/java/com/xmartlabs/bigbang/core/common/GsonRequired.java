package com.xmartlabs.bigbang.core.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Indicates that a field must be present and have a value when deserialized using Gson. */
@Retention(RUNTIME)
@Target(FIELD)
public @interface GsonRequired {
}
