package com.xmartlabs.template.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by mirland on 20/04/2016.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface GsonExclude {
}