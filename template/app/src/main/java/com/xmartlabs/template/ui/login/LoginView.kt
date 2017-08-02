package com.xmartlabs.template.ui.login

import com.xmartlabs.template.ui.common.TemplateView

interface LoginView : TemplateView {
  fun setIsLoading(loading: Boolean)
  fun gotoRecyclerExampleActivity()
}
