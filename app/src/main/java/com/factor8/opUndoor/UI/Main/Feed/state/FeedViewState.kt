package com.factor8.opUndoor.UI.Main.Feed.state

import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken

data class FeedViewState(
        //Feed vars
        var authToken: AuthToken? = null,
        var accountProperties: AccountProperties? = null

        )