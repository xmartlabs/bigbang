package com.xmartlabs.template.ui.onboarding

import com.f2prateek.dart.HensonNavigable
import com.xmartlabs.bigbang.ui.SingleFragmentActivity

@HensonNavigable
class OnboardingActivity : SingleFragmentActivity() {
  override fun createFragment() = OnboardingFragmentBuilder().build()
}
