package com.xmartlabs.template.controller

import com.xmartlabs.bigbang.core.controller.SessionController
import com.xmartlabs.template.model.Session

val SessionController.session
    get() = abstractSession as? Session?
    
