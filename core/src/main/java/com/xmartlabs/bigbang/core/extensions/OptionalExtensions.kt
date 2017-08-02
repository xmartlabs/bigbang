package com.xmartlabs.bigbang.core.extensions

/** If `this` is null, then performs the `action` and returns that value */
fun <T> T?.orDo(action: () -> T) = if (this == null) action() else this

/** Returns `true` if this has a value and that values matches the `predicate` */
fun <T> T?.andMatches(predicate: (T) -> Boolean) = this != null && predicate(this)