package com.xmartlabs.template.ui.onboarding;

import com.annimon.stream.Optional;
import com.xmartlabs.bigbang.ui.mvp.BaseMvpPresenter;

import lombok.Builder;

@Builder
public class OnboardingPresenter extends BaseMvpPresenter<OnboardingView> {
  private static final int FIRST_PAGE = 0;

  private OnboardingPageAdapter pageAdapter;
  @Builder.Default
  private int currentPage = FIRST_PAGE;

  @Override
  public void attachView(OnboardingView view) {
    super.attachView(view);
    pageAdapter = view.createPageAdapter();
    view.setupView(pageAdapter);
  }

  void nextButtonPressed() {
    if (isLastPage()) {
      skipButtonPressed();
    } else {
      currentPage++;
      Optional.ofNullable(getView())
          .ifPresent(view -> view.moveToPage(currentPage));
      handleSkipButtonVisibility();
    }
  }

  public boolean isLastPage() {
    return currentPage == pageAdapter.getCount() - 1;
  }

  private void handleSkipButtonVisibility() {
    Optional.ofNullable(getView())
        .ifPresent(view -> view.setSkipButtonVisibility(!isLastPage()));
  }

  void pageChanged(int newPage) {
    currentPage = newPage;
    Optional.ofNullable(getView())
        .ifPresent(OnboardingView::handleNextButtonVisibility);
    handleSkipButtonVisibility();
  }

  void skipButtonPressed() {
    Optional.ofNullable(getView())
        .ifPresent(OnboardingView::goToLoginActivity);
  }
}
