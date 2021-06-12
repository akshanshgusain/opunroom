package com.factor8.opUndoor.UI.Main.Feed.state

import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent

sealed class FeedStateEvent {

    object GetAccountPropertiesEvent : FeedStateEvent()

    object GetFeedEvent : FeedStateEvent()

    class None : FeedStateEvent()

}