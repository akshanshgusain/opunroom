package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FeedPropertiesGroupStoryPicture(
    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("duration")
    @Expose
    val duaration: String,

    @SerializedName("groupid")
    @Expose
    val groupid: String,

    @SerializedName("groupstory")
    @Expose
    val groupstory: String,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("status")
    @Expose
    val status: String,

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
        return "FeedPropertiesGrouppictures(GroupID: $groupid, url: $groupstory)"
    }
}