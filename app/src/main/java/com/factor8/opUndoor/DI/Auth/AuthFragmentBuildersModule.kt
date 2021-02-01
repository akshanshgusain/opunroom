package com.factor8.opUndoor.DI.Auth

import com.factor8.opUndoor.UI.Auth.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterP1Fragment(): RegisterP1Fragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterP2Fragment(): RegisterP2Fragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterP3Fragment(): RegisterP3Fragment

    @ContributesAndroidInjector()
    abstract fun contributeTransitionFragment(): TransitionFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgetPasswordFragment

}