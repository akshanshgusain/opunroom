package com.factor8.opUndoor.ViewModel.Main

import android.util.Log
import androidx.lifecycle.LiveData
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Repository.Main.AccountRepository
import com.factor8.opUndoor.Repository.Main.FeedRepository
import com.factor8.opUndoor.Session.SessionManager
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Loading
import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent
import com.factor8.opUndoor.UI.Main.Account.state.AccountViewState
import com.factor8.opUndoor.UI.Main.Feed.state.FeedStateEvent
import com.factor8.opUndoor.UI.Main.Feed.state.FeedViewState
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.ViewModel.BaseViewModel
import javax.inject.Inject

class FeedViewModel
@Inject
constructor(val feedRepository: FeedRepository,
            val sessionManager: SessionManager,
            val accountRepository: AccountRepository
) : BaseViewModel<FeedStateEvent, FeedViewState>() {

    override fun handleStateEvent(stateEvent: FeedStateEvent): LiveData<DataState<FeedViewState>> {

        when (stateEvent) {
            is FeedStateEvent.GetAccountPropertiesEvent -> {
                return sessionManager.cachedToken.value?.let {
                    Log.d(TAG, "handleStateEvent: " + it.id + " AccountViewModel")
                    feedRepository.getAccountProperties(it)
                } ?: AbsentLiveData.create()
            }

            is FeedStateEvent.None ->{
                return object : LiveData<DataState<FeedViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = DataState(null, Loading(false), null)
                    }
                }
            }

        }
    }

    override fun initNewViewState(): FeedViewState {
        return FeedViewState()
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if (update.accountProperties == accountProperties) {
            return
        }
        update.accountProperties = accountProperties
        setViewState(update)
    }

    fun cancelActiveJobs() {
        feedRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }

    fun handlePendingData() {
        setStateEvent(FeedStateEvent.None())
    }

}