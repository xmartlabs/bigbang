package com.xmartlabs.template.controller

import com.xmartlabs.bigbang.core.controller.SessionController
import com.xmartlabs.template.model.Session

var SessionController.session
    get() = abstractSession as? Session
    set(value) { saveSession(value) }

fun SessionController.update(block: (Session?) -> Session) { session = block(session) }