package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FeedPropertiesNews(
    @SerializedName("business")
    @Expose
    val business: List<FeedPropertiesNewsDetails>,

    @SerializedName("economictimes")
    @Expose
    val economictimes: List<FeedPropertiesNewsDetails>,

    @SerializedName("science")
    @Expose
    val science: List<FeedPropertiesNewsDetails>,


    @SerializedName("technology")
    @Expose
    val technology: List<FeedPropertiesNewsDetails>,


    @SerializedName("timesofindia")
    @Expose
    val timesofindia: List<FeedPropertiesNewsDetails>
) {
}