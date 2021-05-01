package com.factor8.opUndoor.UI.Main.Feed.state

import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent

sealed class FeedStateEvent {

    class GetAccountPropertiesEvent: FeedStateEvent()

    class None: FeedStateEvent()

}