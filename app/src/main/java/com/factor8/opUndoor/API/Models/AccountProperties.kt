package com.factor8.opUndoor.API.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/* Here id is pk in the server DB Table*/


@Entity(tableName = "account_properties")
data class AccountProperties(

        @SerializedName("id")
        @Expose
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        var id: Int,

        @SerializedName("f_name")
        @Expose
        @ColumnInfo(name = "f_name")
        var f_name: String,

        @SerializedName("l_name")
        @Expose
        @ColumnInfo(name = "l_name")
        var l_name: String,

        @SerializedName("username")
        @Expose
        @ColumnInfo(name = "username")
        var username: String,

        @SerializedName("email")
        @Expose
        @ColumnInfo(name = "email")
        var email: String,

        @SerializedName("picture")
        @Expose
        @ColumnInfo(name = "profile_picture")
        var profile_picture: String,

        @SerializedName("network")
        @Expose
        @ColumnInfo(name = "network")
        var network: String,

        @SerializedName("profession")
        @Expose
        @ColumnInfo(name = "profession")
        var profession: String,

        @SerializedName("experience")
        @Expose
        @ColumnInfo(name = "experience")
        var experience: String,

        @SerializedName("experience")
        @Expose
        @ColumnInfo(name = "is_verified")
        var is_verified: String,

        @SerializedName("coverpic")
        @Expose
        @ColumnInfo(name = "cover_picture")
        var cover_picture: String,

        @SerializedName("privacy")
        @Expose
        @ColumnInfo(name = "privacy")
        var privacy: String,

        @SerializedName("networkprofile")
        @Expose
        @ColumnInfo(name = "network_profile_picture")
        var network_profile_picture: String,

        @SerializedName("networkcover")
        @Expose
        @ColumnInfo(name = "network_cover_picture")
        var network_cover_picture: String,

        @SerializedName("current_company")
        @Expose
        @ColumnInfo(name = "current_company")
        var current_company: String
)