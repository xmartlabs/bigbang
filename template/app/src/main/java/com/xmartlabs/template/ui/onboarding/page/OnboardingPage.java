package com.xmartlabs.template.ui.onboarding.page;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.xmartlabs.template.R;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Parcel
@RequiredArgsConstructor
public enum OnboardingPage {
  //TODO: Replace texts and images for your onboarding
  FIRST(R.string.onboarding_title_1, R.string.onboarding_description_1, R.mipmap.ic_launcher),
  SECOND(R.string.onboarding_title_2, R.string.onboarding_description_2, R.mipmap.ic_launcher),
  THIRD(R.string.onboarding_title_3, R.string.onboarding_description_3, R.mipmap.ic_launcher),
  ;

  @StringRes
  private final int title;
  @StringRes
  private final int description;
  @DrawableRes
  private final int imageRes;
}
