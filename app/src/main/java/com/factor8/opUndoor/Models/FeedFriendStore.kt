package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_friend_store")
data class FeedFriendStore(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_friend_store_pk") //id
    val feed_friend_store_pk: Int,

    // feed_user_store_pk is the primary key of FeedUserStore class which holds info of current user
    @ColumnInfo(name = "feed_user_store_pk")
    val feed_user_store_pk: Int,

    @ColumnInfo(name = "user_id")
    val user_id: Int,

    @ColumnInfo(name = "friend_id")
    val friend_id: Int,

    @ColumnInfo(name = "f_name")
    val f_name: String,

    @ColumnInfo(name = "l_name")
    val l_name: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "network")
    val network: String,

    @ColumnInfo(name = "profession")
    val profession: String,

    @ColumnInfo(name = "profile_picture")
    val profile_picture: String,

    @ColumnInfo(name = "cover_picture")
    val cover_picture: String,

    @ColumnInfo(name = "work_experience")
    val work_experience: String,

    @ColumnInfo(name = "current_company")
    val current_company: String
)