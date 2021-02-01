package com.factor8.opUndoor.API.Auth.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegistrationResponse(

        @SerializedName("message")
        @Expose
        var message: String,

        @SerializedName("status")
        @Expose
        var status: String
){
    override fun toString(): String {
        return "RegistrationResponse(response='$status', Message='$message')"
    }
}