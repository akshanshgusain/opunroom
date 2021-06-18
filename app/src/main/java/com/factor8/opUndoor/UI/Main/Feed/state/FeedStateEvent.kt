package com.factor8.opUndoor.UI.Main.Feed.state

import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent
import com.factor8.opUndoor.UI.Main.Feed.CreateGroupFragment

sealed class FeedStateEvent {

    data class CreateGroup(
        val groupTitle: String,
        val groupParticipants: List<CreateGroupFragment.GroupParticipants>
    ): FeedStateEvent()

    object GetAccountPropertiesEvent : FeedStateEvent()

    object GetFeedEvent : FeedStateEvent()

    class None : FeedStateEvent()

}