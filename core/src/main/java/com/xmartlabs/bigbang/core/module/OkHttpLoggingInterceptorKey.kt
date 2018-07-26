package com.xmartlabs.bigbang.core.module

import dagger.MapKey
import okhttp3.Interceptor
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class OkHttpLoggingInterceptorKey(val value: KClass<out Interceptor>)
