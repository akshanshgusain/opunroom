package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Story picture for Friend and Self Classes

class FeedPropertiesStorypicture(
    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("duaration")
    @Expose
    val duaration: String,


    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("storypicture")
    @Expose
    val storypicture: String,

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("userid")
    @Expose
    val userid: String
) {
    override fun toString(): String {
        return "FeedPropertiesStorypicture(By user: $userid, url: $storypicture)"
    }
}