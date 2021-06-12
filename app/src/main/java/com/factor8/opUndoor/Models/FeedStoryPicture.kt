package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_story_picture")
data class FeedStoryPicture(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_story_picture_pk")
    var feed_story_picture_pk: Int,

    // feed_user_store_pk is the primary key of FeedUserStore class which holds info of current user
    @ColumnInfo(name = "feed_user_store_pk")
    var feed_user_store_pk: Int?,

    // feed_friend_store_pk is the primary key of FeedFriendStore class which holds info of friends
    @ColumnInfo(name = "feed_friend_store_pk")
    val feed_friend_store_pk: Int?,

    @ColumnInfo(name ="user_id")
    var user_id: Int,

    @ColumnInfo(name = "story_picture")
    var story_picture: String,

    @ColumnInfo(name="duration")
    var duration: String,

    @ColumnInfo(name = "type")
    var type: Int,

    @ColumnInfo(name = "time_stamp")
    var time_stamp: String
) {
    override fun toString(): String {
        return "FeedStoryPicture: pk:fee_story_picture_pk belongs to: $user_id, $$story_picture, type: $type"
    }
}