package com.xmartlabs.template.ui.onboarding

import com.xmartlabs.bigbang.ui.mvp.BaseMvpPresenter

class OnboardingPresenter : BaseMvpPresenter<OnboardingView>() {
  companion object {
    private val FIRST_PAGE = 0
  }
  
  val isLastPage: Boolean
    get() = currentPage == pageAdapter.count - 1
  
  private lateinit var pageAdapter: OnboardingPageAdapter
  private var currentPage = FIRST_PAGE
  
  override fun attachView(view: OnboardingView) {
    super.attachView(view)
    pageAdapter = view.createPageAdapter()
    view.setupView(pageAdapter)
  }

  internal fun nextButtonPressed() {
    if (isLastPage) {
      skipButtonPressed()
    } else {
      currentPage++
      view?.let { it.moveToPage(currentPage) }
      handleSkipButtonVisibility()
    }
  }

  private fun handleSkipButtonVisibility() = view?.let { it.setSkipButtonVisibility(!isLastPage) }

  internal fun pageChanged(newPage: Int) {
    currentPage = newPage
    view?.let { it.handleNextButtonVisibility() }
    handleSkipButtonVisibility()
  }

  internal fun skipButtonPressed() = view?.let { it.goToLoginActivity() }
}
