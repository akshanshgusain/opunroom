package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FeedPropertiesCompany(
    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("display_picture")
    @Expose
    val display_picture: String,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("network")
    @Expose
    val network: String,

    @SerializedName("profile_picture")
    @Expose
    val profile_picture: String,

    @SerializedName("storypicture")
    @Expose
    val storypicture: List<FeedPropertiesCompanyStorypicture>,


    @SerializedName("updated_at")
    @Expose
    val updated_at: Any,

    @SerializedName("website")
    @Expose
    val website: String
) {
    override fun toString(): String {
        return "FeedPropertiesCompany(Company Name: $name, Company Website: $website)"
    }
}
