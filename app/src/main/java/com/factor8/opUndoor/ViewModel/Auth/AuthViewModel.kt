package com.factor8.opUndoor.ViewModel.Auth

import androidx.lifecycle.LiveData
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken
import com.factor8.opUndoor.Repository.Auth.AuthRepository
import com.factor8.opUndoor.UI.Auth.State.AuthStateEvent
import com.factor8.opUndoor.UI.Auth.State.AuthStateEvent.*
import com.factor8.opUndoor.UI.Auth.State.AuthViewState
import com.factor8.opUndoor.UI.Auth.State.LoginFields
import com.factor8.opUndoor.UI.Auth.State.RegistrationFields
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.ViewModel.BaseViewModel
import javax.inject.Inject

class AuthViewModel

@Inject
constructor(val authRepository: AuthRepository): BaseViewModel<AuthStateEvent, AuthViewState>(){

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when(stateEvent){

            is LoginAttemptEvent -> {
                return authRepository.attemptLogin(stateEvent.email, stateEvent.password)
            }

            is RegisterAttemptEvent -> {
                return AbsentLiveData.create()
            }

            is CheckPreviousAuthEvent -> {
                return authRepository.checkPreviousAuthUser()
            }


        }
    }

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }

    fun setRegistrationFields(registrationFields: RegistrationFields){
        val update = getCurrentViewStateOrNew()
        if(update.registrationFields == registrationFields){
            return
        }
        update.registrationFields = registrationFields
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields){
        val update = getCurrentViewStateOrNew()
        if(update.loginFields == loginFields){
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }

    fun setAuthToken(authToken: AuthToken){
        val update = getCurrentViewStateOrNew()
        if(update.authToken == authToken){
            return
        }
        update.authToken = authToken
        _viewState.value = update
    }

    fun setAccountProperties(accountProperties: AccountProperties){
        val update = getCurrentViewStateOrNew()
        if(update.accountProperties == accountProperties){
            return
        }
        update.accountProperties = accountProperties
        _viewState.value = update
    }

    fun cancelActiveJobs(){
        authRepository.cancelActiveJobs()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}