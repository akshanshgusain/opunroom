package com.factor8.opUndoor.API.Auth.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForgetPasswordResponse(

        @SerializedName("message")
        @Expose
        var message: String?,

        @SerializedName("status")
        @Expose
        var status: Int?
) {
    override fun toString(): String {
        return "ForgetPasswordResponse(response='$status', Message='$message')"
    }
}