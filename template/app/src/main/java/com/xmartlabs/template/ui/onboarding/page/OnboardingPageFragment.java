package com.xmartlabs.template.ui.onboarding.page;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.xmartlabs.bigbang.ui.BaseFragment;
import com.xmartlabs.template.R;

import butterknife.BindView;

@FragmentWithArgs
public class OnboardingPageFragment extends BaseFragment {
  @BindView(R.id.title)
  TextView titleView;
  @BindView(R.id.description)
  TextView descriptionView;
  @BindView(R.id.image)
  ImageView imageView;

  @Arg(bundler = ParcelerArgsBundler.class)
  OnboardingPage onboardingPage;

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_onboarding_page;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    titleView.setText(onboardingPage.getTitle());
    descriptionView.setText(onboardingPage.getDescription());
    //noinspection deprecation
    imageView.setImageDrawable(getContext().getResources().getDrawable(onboardingPage.getImageRes()));
  }
}
