package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_user_store")
data class FeedUserStore(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_user_store_pk")
    var feed_user_store_pk: Int,

    // feed_user_store_pk is the primary key of FeedUserStore class which holds info of current user
    @ColumnInfo(name = "f_name")
    var f_name: String,

    @ColumnInfo(name = "l_name")
    var l_name: String,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "network")
    var network: String,

    @ColumnInfo(name = "profile_picture")
    var picture: String,

    @ColumnInfo(name = "cover_picture")
    var cover_picture: String,

    @ColumnInfo(name = "profession")
    var profession: String,

    @ColumnInfo(name="work_experience")
    var work_experience: String,

    @ColumnInfo(name="current_company")
    var current_company: String
) {
    override fun toString(): String {
        return "UserFeedStore( pk/id = $feed_user_store_pk, full name = $f_name $l_name)"
    }
}