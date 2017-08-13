package com.xmartlabs.template.ui.onboarding

import com.xmartlabs.template.ui.common.TemplatePresenter
import javax.inject.Inject

class OnboardingPresenter @Inject constructor() : TemplatePresenter<OnboardingView>() {
  companion object {
    private val FIRST_PAGE = 0
  }

  val isLastPage: Boolean
    get() = currentPage == pageAdapter.count - 1

  @Suppress("LateinitUsage")
  private lateinit var pageAdapter: OnboardingPageAdapter
  private var currentPage = FIRST_PAGE

  override fun attachView(view: OnboardingView) {
    super.attachView(view)
    pageAdapter = view.createPageAdapter()
    view.setup(pageAdapter)
  }

  internal fun nextButtonPressed() {
    if (isLastPage) {
      skipButtonPressed()
    } else {
      currentPage++
      view?.moveToPage(currentPage)
      handleSkipButtonVisibility()
    }
  }

  private fun handleSkipButtonVisibility() = view?.setSkipButtonVisibility(!isLastPage)

  internal fun pageChanged(newPage: Int) {
    currentPage = newPage
    view?.handleNextButtonVisibility()
    handleSkipButtonVisibility()
  }

  internal fun skipButtonPressed() = view?.goToLoginActivity()
}
