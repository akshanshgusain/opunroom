package com.factor8.opUndoor.API.Auth

import androidx.lifecycle.LiveData
import com.factor8.opUndoor.API.Auth.Responses.ForgetPasswordResponse
import com.factor8.opUndoor.API.Auth.Responses.RegistrationResponse
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Util.GenericApiResponse
import okhttp3.MultipartBody
import retrofit2.http.*


// All of the retrofit request AUTH COMPONENT

interface OpUndoorAuthService {

    @POST("login")
    @FormUrlEncoded
    fun login(
            @Field("username") email: String,
            @Field("password") password: String
    ): LiveData<GenericApiResponse<AccountProperties>>

    @Multipart
    @POST("register")
    @FormUrlEncoded
    fun register(
            @Part("f_name") f_name: String,
            @Part("l_name") l_name: String,
            @Part("username") username: String,
            @Part("email") email: String,
            @Part("password") password: String,
            @Part("network") network: String,
            @Part("profession") profession: String,
            @Part("experience") experience: String,
            @Part("current_company") current_company: String,
            @Part("picture") image: MultipartBody.Part?
    ): LiveData<GenericApiResponse<RegistrationResponse>>


    @POST("forgotpassword")
    @FormUrlEncoded
    fun forgetPassword(
            @Field("email") email: String
    ): LiveData<GenericApiResponse<ForgetPasswordResponse>>

}