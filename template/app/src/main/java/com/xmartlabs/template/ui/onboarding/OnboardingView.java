package com.xmartlabs.template.ui.onboarding;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.xmartlabs.template.ui.common.TemplateView;

public interface OnboardingView extends TemplateView {
  OnboardingPageAdapter createPageAdapter();
  void setupView(@NonNull OnboardingPageAdapter pageAdapter);
  void setSkipButtonVisibility(boolean visible);
  void moveToPage(int page);
  void startActivity(Intent intent);
  void handleNextButtonVisibility();
  void goToLoginActivity();
}
