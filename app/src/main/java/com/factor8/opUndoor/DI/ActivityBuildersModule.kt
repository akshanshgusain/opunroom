package com.factor8.opUndoor.DI



import com.factor8.opUndoor.DI.Auth.AuthFragmentBuildersModule
import com.factor8.opUndoor.DI.Auth.AuthModule
import com.factor8.opUndoor.DI.Auth.AuthScope
import com.factor8.opUndoor.DI.Auth.AuthViewModelModule
import com.factor8.opUndoor.DI.Main.MainFragmentBuilderModule
import com.factor8.opUndoor.DI.Main.MainModule
import com.factor8.opUndoor.DI.Main.MainScope
import com.factor8.opUndoor.DI.Main.MainViewModelModule
import com.factor8.opUndoor.UI.Auth.AuthActivity
import com.factor8.opUndoor.UI.Main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity


    @MainScope
    @ContributesAndroidInjector(
            modules = [MainModule::class, MainFragmentBuilderModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}