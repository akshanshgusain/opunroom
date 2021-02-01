package com.factor8.opUndoor.DI.Auth

import androidx.lifecycle.ViewModel
import com.factor8.opUndoor.DI.ViewModelKey
import com.factor8.opUndoor.ViewModel.Auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}