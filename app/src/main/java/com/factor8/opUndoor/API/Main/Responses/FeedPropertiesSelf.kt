package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FeedPropertiesSelf(
    @SerializedName("coverpic")
    @Expose
    val coverpic: String,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("current_company")
    @Expose
    val current_company: String,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("experience")
    @Expose
    val experience: String,

    @SerializedName("f_name")
    @Expose
    val f_name: String,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("is_verified")
    @Expose
    val is_verified: String,


    @SerializedName("l_name")
    @Expose
    val l_name: String,

    @SerializedName("network")
    @Expose
    val network: String,

    @SerializedName("password")
    @Expose
    val password: String,

    @SerializedName("picture")
    @Expose
    val picture: String,

    @SerializedName("privacy")
    @Expose
    val privacy: String,

    @SerializedName("profession")
    @Expose
    val profession: String,


    @SerializedName("remember_token")
    @Expose
    val remember_token: Any,

    @SerializedName("storypicture")
    @Expose
    val storypicture: List<FeedPropertiesStorypicture>,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("username")
    @Expose
    val username: String
) {
    override fun toString(): String {
        return "FeedPropertiesSelf(Name: $f_name $l_name, username: $username)"
    }
}