package com.xmartlabs.bigbang.core.log

/** Information associated with a captured exception or event-like exception.  */
data class LogInfo(val priority: Int, val tag: String? = null, val message: String? = null)
