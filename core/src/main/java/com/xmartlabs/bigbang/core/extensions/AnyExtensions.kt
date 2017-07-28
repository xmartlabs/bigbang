package com.xmartlabs.bigbang.core.extensions

/** Executes the `block` on `this` and, if an exception is thrown, returns null instead */
fun <T, R> T.ignoreException(block: T.() -> R) = try { block(this) } catch (e: Exception) { null }

/** Executes the `block` on `this` and, if an exception is thrown, it's handled by `handler` */
fun <T, R> T.ifException(block: (T) -> R, handler: (Exception) -> Unit) =
    try { block(this) } catch (e: Exception) { handler(e); null }

/** Executes `block` and, if an exception is thrown, it's handled by `handler` */
fun <T> handleException(handler: (Exception) -> Unit, block: () -> T?) =
    try { block() } catch (e: Exception) { handler(e); null }
