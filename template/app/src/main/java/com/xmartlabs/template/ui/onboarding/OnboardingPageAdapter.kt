package com.xmartlabs.template.ui.onboarding

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.xmartlabs.template.ui.onboarding.page.OnboardingPage
import com.xmartlabs.template.ui.onboarding.page.OnboardingPageFragment

//TODO: Fix the initialization
class OnboardingPageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
  private val pages: List<OnboardingPageFragment> = OnboardingPage.values().map { page -> OnboardingPageFragment() }

  override fun getItem(position: Int): Fragment {
    return pages[position]
  }

  override fun getCount(): Int {
    return pages.size
  }
}
