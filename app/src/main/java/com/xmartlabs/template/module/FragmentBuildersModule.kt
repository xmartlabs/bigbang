package com.xmartlabs.template.module

import com.xmartlabs.bigbang.ui.BaseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeBaseFragment(): BaseFragment
}
