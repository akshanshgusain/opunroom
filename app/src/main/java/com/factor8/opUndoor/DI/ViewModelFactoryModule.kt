package com.factor8.opUndoor.DI

import androidx.lifecycle.ViewModelProvider
import com.factor8.opUndoor.ViewModel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}