package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class FeedResponse (
    @SerializedName("company")
    @Expose
    val company: List<FeedPropertiesCompany>,

    @SerializedName("friends")
    @Expose
    val friends: List<FeedPropertiesFriend>,

    @SerializedName("groups")
    @Expose
    val groups: List<FeedPropertiesGroup>,

    @SerializedName("network")
    @Expose
    val network: List<FeedPropertiesNetwork>,

    @SerializedName("news")
    @Expose
    val news: FeedPropertiesNews,

    @SerializedName("self")
    @Expose
    val self: FeedPropertiesSelf,

    @SerializedName("status")
    @Expose
    val status: Int,

    @SerializedName("userstory")
    @Expose
    val userstory: List<FeedPropertiesStorypicture>
){
    override fun toString(): String {
        return "FeedSearchResponse(status = $status, self = $self)"
    }
}