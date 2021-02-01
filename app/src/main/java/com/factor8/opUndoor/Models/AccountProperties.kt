package com.factor8.opUndoor.Models

import androidx.annotation.Nullable
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
        @Nullable
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        var id: Int,

        @SerializedName("f_name")
        @Expose
        @Nullable
        @ColumnInfo(name = "f_name")
        var f_name: String,

        @SerializedName("l_name")
        @Expose
        @Nullable
        @ColumnInfo(name = "l_name")
        var l_name: String,

        @SerializedName("username")
        @Expose
        @Nullable
        @ColumnInfo(name = "username")
        var username: String,

        @SerializedName("email")
        @Expose
        @Nullable
        @ColumnInfo(name = "email")
        var email: String,

        @SerializedName("picture")
        @Expose
        @Nullable
        @ColumnInfo(name = "profile_picture")
        var profile_picture: String,

        @SerializedName("network")
        @Expose
        @Nullable
        @ColumnInfo(name = "network")
        var network: String,

        @SerializedName("profession")
        @Expose
        @Nullable
        @ColumnInfo(name = "profession")
        var profession: String,

        @SerializedName("experience")
        @Expose
        @Nullable
        @ColumnInfo(name = "experience")
        var experience: String,

        @SerializedName("is_verified")
        @Expose
        @Nullable
        @ColumnInfo(name = "is_verified")
        var is_verified: String,

        @SerializedName("coverpic")
        @Expose
        @Nullable
        @ColumnInfo(name = "cover_picture")
        var cover_picture: String,

        @SerializedName("privacy")
        @Expose
        @Nullable
        @ColumnInfo(name = "privacy")
        var privacy: String,

        @SerializedName("networkprofile")
        @Expose
        @Nullable
        @ColumnInfo(name = "network_profile_picture")
        var network_profile_picture: String,

        @SerializedName("networkcover")
        @Expose
        @Nullable
        @ColumnInfo(name = "network_cover_picture")
        var network_cover_picture: String,

        @SerializedName("current_company")
        @Expose
        @Nullable
        @ColumnInfo(name = "current_company")
        var current_company: String
){
        override fun toString(): String {
                return "LoginResponse(id = '$id',f_name = '$f_name', l_name = '$l_name', network = '$network' )"
        }
}