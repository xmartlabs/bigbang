package com.xmartlabs.template.ui.onboarding

import android.content.Intent

import com.xmartlabs.template.ui.common.TemplateView

interface OnboardingView : TemplateView {
  fun createPageAdapter(): OnboardingPageAdapter
  fun setupView(pageAdapter: OnboardingPageAdapter)
  fun setSkipButtonVisibility(visible: Boolean)
  fun moveToPage(page: Int)
  fun startActivity(intent: Intent)
  fun handleNextButtonVisibility()
  fun goToLoginActivity()
}
