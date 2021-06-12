package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FeedPropertiesNewsDetails(
    @SerializedName("company")
    @Expose
    val company: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("guid")
    @Expose
    val guid: String,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("pubDate")
    @Expose
    val pubDate: String,

    @SerializedName("title")
    @Expose
    val title: String
) {
    override fun toString(): String {
        return "FeedPropertiesNewsNewsPaper(Newspaer: $company)"
    }
}