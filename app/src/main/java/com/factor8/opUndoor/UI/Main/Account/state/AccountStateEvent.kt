package com.factor8.opUndoor.UI.Main.Account.state

import android.net.Uri
import okhttp3.MultipartBody

sealed class AccountStateEvent {

    class GetAccountPropertiesEvent: AccountStateEvent()

    data class UpdateUserProfileEvent(
            val firstName: String,
            val lastName: String,
            val currentCompany:String,
            val fieldOfWork: String,
            val network: String,
            val totalWorkExperience: String,
            val email: String,
            val username: String,
            val makeAccountPublic: String,
            val displayPicture: MultipartBody.Part,
            val coverPicture: MultipartBody.Part
    ): AccountStateEvent()

    class None: AccountStateEvent()

}