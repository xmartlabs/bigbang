package com.xmartlabs.template.service.gson;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Annotation for fields that must be present and have a value when deserialized using Gson */
@Retention(RUNTIME)
@Target(FIELD)
public @interface GsonRequired {
}
