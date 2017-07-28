package com.xmartlabs.template.ui.onboarding.page

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler
import com.xmartlabs.bigbang.ui.BaseFragment
import com.xmartlabs.template.R

import butterknife.BindView

@FragmentWithArgs
class OnboardingPageFragment : BaseFragment() {
  @BindView(R.id.title)
  internal lateinit var titleView: TextView
  @BindView(R.id.description)
  internal lateinit var descriptionView: TextView
  @BindView(R.id.image)
  internal lateinit var imageView: ImageView

  @Arg
  internal lateinit var onboardingPage: OnboardingPage

  @LayoutRes
  override fun getLayoutResId(): Int {
    return R.layout.fragment_onboarding_page
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    titleView.setText(onboardingPage.title)
    descriptionView.setText(onboardingPage.description)
    
    //noinspection deprecation
    imageView.setImageDrawable(context.resources.getDrawable(onboardingPage.image))
  }
}
