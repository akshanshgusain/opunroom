package com.factor8.opUndoor.ViewModel.Main

import android.util.Log
import androidx.lifecycle.LiveData
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.Relationships.FeedCompanyToFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedFriendStoreToFeedStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedGroupStoreToFeedGroupStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedNetworkStoreAndFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Wrappers.FeedLoadFromCache
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
import com.factor8.opUndoor.Util.Constants.Companion.STORY_TYPE_COMPANY_STORY
import com.factor8.opUndoor.Util.Constants.Companion.STORY_TYPE_FRIEND_STORY
import com.factor8.opUndoor.Util.Constants.Companion.STORY_TYPE_GROUP_STORY
import com.factor8.opUndoor.ViewModel.BaseViewModel
import javax.inject.Inject

class FeedViewModel
@Inject
constructor(
    private val feedRepository: FeedRepository,
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

            is FeedStateEvent.GetFeedEvent -> {
                return sessionManager.cachedToken.value?.let {
                    feedRepository.getFeed(it)
                } ?: AbsentLiveData.create()
            }

            is FeedStateEvent.CreateGroup -> {
                return sessionManager.cachedToken.value?.let{
                    feedRepository.attemptCreateGroup(
                        stateEvent.groupTitle,
                        stateEvent.groupParticipants,
                        it
                    )
                }?: AbsentLiveData.create()
            }

            is FeedStateEvent.None -> {
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

    // Update View State
    fun setAccountPropertiesData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if (update.accountProperties == accountProperties) {
            return
        }
        update.accountProperties = accountProperties
        setViewState(update)
    }

    fun setFeedLoadFromCache(feedLoadFromCache: FeedLoadFromCache) {
        val update = getCurrentViewStateOrNew()
        if (update.feedLoadFromCache == feedLoadFromCache) {
            return
        }
        update.feedLoadFromCache = feedLoadFromCache
        setViewState(update)
    }
    // Update View State

    fun setStoryList(listType: Int, storyObj: Any) {
        val update = getCurrentViewStateOrNew()
        val storyList = FeedViewState.StoryList(listType = listType)
        when (listType) {
            STORY_TYPE_FRIEND_STORY -> {
                storyList.friendStory = storyObj as FeedFriendStoreToFeedStoryPicture
            }
            STORY_TYPE_GROUP_STORY -> {
                storyList.groupStory = storyObj as FeedGroupStoreToFeedGroupStoryPicture
            }
            STORY_TYPE_COMPANY_STORY -> {
                storyList.companyStory = storyObj as FeedCompanyToFeedCompanyStoryPicture
            }
            else -> {
                storyList.networkStory = storyObj as FeedNetworkStoreAndFeedCompanyStoryPicture
            }
        }

        if (update.storyList == storyList) {
            return
        }

        update.storyList = storyList
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