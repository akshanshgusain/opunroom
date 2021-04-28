package com.factor8.opUndoor.UI.Main.Account.state

import android.net.Uri
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken

data class AccountViewState(

        //Account vars
        var authToken: AuthToken? = null,
        var accountProperties: AccountProperties? = null,


        //Account Settings vars
        var accountSettings: AccountSettingsFields = AccountSettingsFields(),

        //Account Setting ViewState
        var updatedProfileFields: UpdatedProfileFields = UpdatedProfileFields()
) {
    data class AccountSettingsFields(
            var firstName: String = "",
            var lastName: String = "",
            var currentCompany: String = "",
            var fieldOfWork: String = "",
            var workExperience: String = "",
            var email: String = "",
            var username: String = "",
            var isPublic: Boolean = false
    )

    data class UpdatedProfileFields(
            var firstName: String? = "",
            var LastName: String? = "",
            var currentCompany: String? = "",
            var fieldOfWork: String? = "",
            var totalWorkExperience: String? = "",
            var email: String? = "",
            var username: String? = "",
            var makeAccountPublic: String? = "",
            var displayPicture: Uri? = null,
            var coverPicture: Uri? = null
    )
}