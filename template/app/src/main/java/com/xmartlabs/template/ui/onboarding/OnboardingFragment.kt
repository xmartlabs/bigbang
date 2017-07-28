package com.xmartlabs.template.ui.onboarding

import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.template.R
import com.xmartlabs.template.App
import com.xmartlabs.template.ui.common.TemplateFragment

import butterknife.BindView
import butterknife.OnClick
import butterknife.OnPageChange
import com.xmartlabs.template.ui.Henson
import javax.inject.Inject

@FragmentWithArgs
class OnboardingFragment : TemplateFragment<OnboardingView, OnboardingPresenter>(), OnboardingView {
  companion object {
    private val ALPHA_INVISIBLE = 0.01f
    private val ALPHA_OPAQUE = 1.0f
    private val ALPHA_START_DELAY_MILLISECONDS: Long = 100
  }
  
  @BindView(R.id.view_pager)
  internal lateinit var viewPager: ViewPager
  @BindView(R.id.skip_button)
  internal lateinit var skipButton: TextView
  @BindView(R.id.start_button)
  internal lateinit var startButton: Button
  @BindView(R.id.next_button)
  internal lateinit var nextButton: TextView

  @Inject
  internal lateinit var presenter: OnboardingPresenter
  
  override fun createPresenter() = presenter

  @LayoutRes
  override fun getLayoutResId() = R.layout.fragment_onboarding

  override fun createPageAdapter() = OnboardingPageAdapter(childFragmentManager)

  override fun setupView(pageAdapter: OnboardingPageAdapter) {
    viewPager.adapter = pageAdapter
    listOf(startButton, nextButton).forEach { it.setOnClickListener { presenter.nextButtonPressed() } }
    skipButton.setOnClickListener { presenter.skipButtonPressed() }
  }

  @OnPageChange(R.id.view_pager)
  internal fun onPageChanged(position: Int) = presenter.pageChanged(position)

  override fun setSkipButtonVisibility(visible: Boolean) {
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
