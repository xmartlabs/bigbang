package com.xmartlabs.template.ui.onboarding

import android.content.Intent
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.template.App
import com.xmartlabs.template.R
import com.xmartlabs.template.helper.EmptyOnPageChangeListener
import com.xmartlabs.template.ui.Henson
import com.xmartlabs.template.ui.common.TemplateFragment
import kotlinx.android.synthetic.main.fragment_onboarding.*
import javax.inject.Inject

@FragmentWithArgs
class OnboardingFragment : TemplateFragment<OnboardingView, OnboardingPresenter>(), OnboardingView {
  companion object {
    private const val ALPHA_INVISIBLE = 0.01f
    private const val ALPHA_OPAQUE = 1.0f
    private const val ALPHA_START_DELAY_MILLISECONDS: Long = 100
  }

  @Inject
  override lateinit var presenter: OnboardingPresenter

  override val layoutResId = R.layout.fragment_onboarding

  override fun createPageAdapter() = OnboardingPageAdapter(childFragmentManager)

  override fun setup(pageAdapter: OnboardingPageAdapter) {
    viewPager.adapter = pageAdapter
    listOf(startButton, nextButton).forEach { it.setOnClickListener { presenter.nextButtonPressed() } }
    skipButton.setOnClickListener { presenter.skipButtonPressed() }
    viewPager.addOnPageChangeListener(object : EmptyOnPageChangeListener() {
      override fun onPageSelected(position: Int) = presenter.pageChanged(position)
    })
  }

  override fun setSkipButtonVisibility(visible: Boolean) {
    @Suppress("ComplexCondition")
    if (visible && skipButton.alpha == ALPHA_OPAQUE || !visible && skipButton.alpha == View.INVISIBLE.toFloat()) {
      return
    }

    skipButton.animate()
        .alpha(if (visible) ALPHA_OPAQUE else ALPHA_INVISIBLE)
        .start()
  }

  override fun moveToPage(page: Int) {
    viewPager.setCurrentItem(page, true)
    handleNextButtonVisibility()
  }

  override fun handleNextButtonVisibility() {
    if (presenter.isLastPage) {
      swapView(nextButton, startButton)
    } else if (nextButton.alpha <= ALPHA_INVISIBLE) {
      swapView(startButton, nextButton)
    }
  }

  private fun swapView(fromView: View, toView: View) {
    fromView.animate()
        .alpha(ALPHA_INVISIBLE)
        .start()
    toView.animate()
        .alpha(ALPHA_OPAQUE)
        .setStartDelay(ALPHA_START_DELAY_MILLISECONDS)
        .start()
  }

  override fun goToLoginActivity() {
    val intent = Henson.with(App.context)
        .gotoLoginActivity()
        .build()
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
  }
}
