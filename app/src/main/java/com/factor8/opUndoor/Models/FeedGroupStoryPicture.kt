package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_group_story_picture")
data class FeedGroupStoryPicture(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_group_story_picture_pk")
    val feed_group_story_picture: Int,

    // feed_group_store_pk is the primary key of FeedGroupStore class which holds info of current groups
    @ColumnInfo(name = "feed_group_store_pk")
    val feed_group_store_pk: Int,

    @ColumnInfo(name = "group_id")
    val group_id: Int,

    @ColumnInfo(name = "user_id")
    val user_id: Int,

    @ColumnInfo(name = "story_picture")
    val story_picture: String,

    @ColumnInfo(name = "duration")
    val duration: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "created_at")
    val created_at: String
){
    override fun toString(): String {
        return "FeedGroupStoryPicture GroupId: $group_id, UserId: $user_id, Type: $type, Duration: $duration, Time: $created_at, StoryPicture: $story_picture"
    }
}
