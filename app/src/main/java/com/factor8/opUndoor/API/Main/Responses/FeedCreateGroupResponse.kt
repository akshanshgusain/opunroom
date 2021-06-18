package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FeedCreateGroupResponse(
    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("status")
    @Expose
    val status: Int
)