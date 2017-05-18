package com.xmartlabs.template.ui.onboarding;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.annimon.stream.Stream;
import com.xmartlabs.template.ui.onboarding.page.OnboardingPage;
import com.xmartlabs.template.ui.onboarding.page.OnboardingPageFragment;
import com.xmartlabs.template.ui.onboarding.page.OnboardingPageFragmentBuilder;

import java.util.List;

class OnboardingPageAdapter extends FragmentStatePagerAdapter {
  private final List<OnboardingPageFragment> pages;

  OnboardingPageAdapter(@NonNull FragmentManager fragmentManager) {
    super(fragmentManager);

    pages = Stream.of(OnboardingPage.values())
        .map(page -> new OnboardingPageFragmentBuilder(page).build())
        .toList();
  }

  @Override
  public Fragment getItem(int position) {
    return pages.get(position);
  }

  @Override
  public int getCount() {
    return pages.size();
  }
}
