package com.xmartlabs.template.ui.onboarding;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.template.R;
import com.xmartlabs.template.TemplateApplication;
import com.xmartlabs.template.ui.Henson;
import com.xmartlabs.template.ui.common.TemplateFragment;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

@FragmentWithArgs
public class OnboardingFragment extends TemplateFragment<OnboardingView, OnboardingPresenter>
    implements OnboardingView {
  private static final float ALPHA_INVISIBLE = 0.01f;
  private static final float ALPHA_OPAQUE = 1.0f;
  private static final long ALPHA_START_DELAY_MILLISECONDS = 100;

  @BindView(R.id.view_pager)
  ViewPager viewPager;
  @BindView(R.id.skip_button)
  TextView skipButton;
  @BindView(R.id.start_button)
  Button startButton;
  @BindView(R.id.next_button)
  TextView nextButton;

  @NonNull
  private OnboardingPresenter presenter;

  @NonNull
  @Override
  protected OnboardingPresenter createPresenter() {
    presenter = OnboardingPresenter.builder().build();
    return presenter;
  }

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_onboarding;
  }

  @Override
  public OnboardingPageAdapter createPageAdapter() {
    return new OnboardingPageAdapter(getChildFragmentManager());
  }

  @Override
  public void setupView(@NonNull OnboardingPageAdapter pageAdapter) {
    viewPager.setAdapter(pageAdapter);
  }

  @OnPageChange(R.id.view_pager)
  void onPageChanged(int position) {
    presenter.pageChanged(position);
  }

  @Override
  public void setSkipButtonVisibility(boolean visible) {
    if ((visible && skipButton.getAlpha() == ALPHA_OPAQUE)
        || (!visible && skipButton.getAlpha() == View.INVISIBLE)) {
      return;
    }

    skipButton.animate()
        .alpha(visible ? ALPHA_OPAQUE : ALPHA_INVISIBLE)
        .start();
  }

  @Override
  public void moveToPage(int page) {
    viewPager.setCurrentItem(page, true);
    handleNextButtonVisibility();
  }

  @Override
  public void handleNextButtonVisibility() {
    if (presenter.isLastPage()) {
      swapView(nextButton, startButton);
    } else if (nextButton.getAlpha() <= ALPHA_INVISIBLE) {
      swapView(startButton, nextButton);
    }
  }

  private void swapView(@NonNull View fromView, @NonNull View toView) {
    fromView.animate()
        .alpha(ALPHA_INVISIBLE)
        .start();
    toView.animate()
        .alpha(ALPHA_OPAQUE)
        .setStartDelay(ALPHA_START_DELAY_MILLISECONDS)
        .start();
  }

  @OnClick({R.id.start_button, R.id.next_button})
  void onNextButtonClick() {
    presenter.nextButtonPressed();
  }

  @OnClick(R.id.skip_button)
  void onSkipButtonClick() {
    presenter.skipButtonPressed();
  }

  @Override
  public void goToLoginActivity() {
    Intent intent = Henson.with(TemplateApplication.getContext())
        .gotoLoginActivity()
        .build()
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }
}
