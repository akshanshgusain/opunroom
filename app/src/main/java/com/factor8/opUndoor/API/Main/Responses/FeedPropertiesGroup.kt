package com.factor8.opUndoor.API.Main.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FeedPropertiesGroup(
    @SerializedName("adminname")
    @Expose
    val adminname: String,

    @SerializedName("adminpicture")
    @Expose
    val adminpicture: String,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("groupadminid")
    @Expose
    val groupadminid: String,

    @SerializedName("grouppictures")
    @Expose
    val grouppictures: List<FeedPropertiesGroupStoryPicture>,

    @SerializedName("grouptitle")
    @Expose
    val grouptitle: String,

    @SerializedName("groupuserid")
    @Expose
    val groupuserid: String,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("no_of_members")
    @Expose
    val no_of_member: Int,


    @SerializedName("updated_at")
    @Expose
    val updated_at: String
){

    override fun toString(): String {
        return "FeedPropertiesGroup(Group Name: $grouptitle, Group Admin: $groupadminid, Group No. members: $no_of_member)"
    }
}