package com.xmartlabs.template.ui.onboarding.page

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.xmartlabs.bigbang.ui.common.parcel.ParcelerEnumTypeConverter
import com.xmartlabs.template.R
import org.parceler.Parcel

@Parcel(converter=OnboardingPageTypeConverter::class)
enum class OnboardingPage(@StringRes val title: Int, @StringRes val description: Int, @DrawableRes val image: Int) {
  //TODO: Replace texts and images for your onboarding
  FIRST(R.string.onboarding_title_1, R.string.onboarding_description_1, R.mipmap.ic_launcher),
  SECOND(R.string.onboarding_title_2, R.string.onboarding_description_2, R.mipmap.ic_launcher),
  THIRD(R.string.onboarding_title_3, R.string.onboarding_description_3, R.mipmap.ic_launcher),
}

class OnboardingPageTypeConverter : ParcelerEnumTypeConverter<OnboardingPage>(OnboardingPage::class.java)
