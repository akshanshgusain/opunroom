package com.factor8.opUndoor.UI.Main.Account.state

import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken

data class AccountViewState(
        var authToken: AuthToken? = null,
        var accountProperties: AccountProperties? = null
)