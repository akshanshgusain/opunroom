package com.factor8.opUndoor.ViewModel.Main

import androidx.lifecycle.LiveData
import com.factor8.opUndoor.Repository.Main.AccountRepository
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent
import com.factor8.opUndoor.UI.Main.Account.state.AccountViewState
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.ViewModel.BaseViewModel
import javax.inject.Inject

class AccountViewModel

@Inject
constructor(accountRepository: AccountRepository): BaseViewModel<AccountStateEvent, AccountViewState>(){

    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        return AbsentLiveData.create()
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

}