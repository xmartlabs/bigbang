package com.xmartlabs.template.ui.onboarding.page

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler
import com.xmartlabs.bigbang.ui.BaseFragment
import com.xmartlabs.template.R

import kotlinx.android.synthetic.main.fragment_onboarding_page.*

@FragmentWithArgs
class OnboardingPageFragment : BaseFragment() {
  @Arg(bundler = ParcelerArgsBundler::class)
  internal lateinit var onboardingPage: OnboardingPage

  override val layoutResId = R.layout.fragment_onboarding_page

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    title.setText(onboardingPage.title)
    description.setText(onboardingPage.description)

    @Suppress("DEPRECATION")
    image.setImageDrawable(context.resources.getDrawable(onboardingPage.image))
  }
}
