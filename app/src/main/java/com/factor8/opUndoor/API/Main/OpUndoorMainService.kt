package com.factor8.opUndoor.API.Main

import androidx.lifecycle.LiveData
import com.factor8.opUndoor.API.Auth.Responses.RegistrationResponse
import com.factor8.opUndoor.API.Main.Responses.UpdatedUserProfileResponse
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Util.GenericApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// All of the retrofit request MAIN COMPONENT

interface OpUndoorMainService {

    @Multipart
    @POST("update")
    fun updateUserProfile(
            @Part("id") id: RequestBody,
            @Part("f_name") f_name: RequestBody,
            @Part("l_name") l_name: RequestBody,
            @Part("username") username: RequestBody,
            @Part("email") email: RequestBody,
            @Part("network") network: RequestBody,
            @Part("profession") profession: RequestBody,
            @Part("privacy") privacy: RequestBody,
            @Part("experience") experience: RequestBody,
            @Part("current_company") current_company: RequestBody,
            @Part image: List<MultipartBody.Part?>
    ): LiveData<GenericApiResponse<UpdatedUserProfileResponse>>

}