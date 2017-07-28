package com.xmartlabs.bigbang.core.helpers

data class TestingTreeNode(
    var priority: Int,
    var tag: String?,
    var message: String? = null,
    var throwable: Throwable? = null
)
