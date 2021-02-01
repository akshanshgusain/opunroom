package com.factor8.opUndoor.ViewModel.Main

import androidx.lifecycle.LiveData
import com.factor8.opUndoor.Repository.Main.FeedRepository
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Main.Feed.state.FeedStateEvent
import com.factor8.opUndoor.UI.Main.Feed.state.FeedViewState
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.ViewModel.BaseViewModel
import javax.inject.Inject

class FeedViewModel
@Inject
constructor(feedRepository: FeedRepository) : BaseViewModel<FeedStateEvent, FeedViewState>() {
    override fun handleStateEvent(stateEvent: FeedStateEvent): LiveData<DataState<FeedViewState>> {
        return AbsentLiveData.create()
    }

    override fun initNewViewState(): FeedViewState {
            return FeedViewState()
    }

}