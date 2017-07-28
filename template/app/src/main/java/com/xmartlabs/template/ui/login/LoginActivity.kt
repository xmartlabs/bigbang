package com.xmartlabs.template.ui.login

import com.f2prateek.dart.HensonNavigable
import com.xmartlabs.bigbang.ui.SingleFragmentActivity

@HensonNavigable
class LoginActivity : SingleFragmentActivity() {
  override fun createFragment() = LoginFragmentBuilder().build()
}
