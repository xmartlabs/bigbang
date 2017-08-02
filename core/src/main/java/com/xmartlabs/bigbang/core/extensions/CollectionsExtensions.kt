package com.xmartlabs.bigbang.core.extensions

/** Converts any `List` to an `Array` */
inline fun <reified T> List<T>.toArray() = Array(this.count(), this::get)
