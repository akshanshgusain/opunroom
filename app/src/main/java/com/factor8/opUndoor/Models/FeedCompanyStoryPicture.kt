package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_company_story_picture")
class FeedCompanyStoryPicture(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_company_story_picture_pk")
    val feed_company_story_picture:Int,

    // feed_company_pk is the primary key of FeedCompany class which holds info of user's current company
    @ColumnInfo(name = "feed_company_pk")
    val feed_company_pk: Int?,

    // feed_network_pk is the primary key of FeedNetworkStore class which holds info of networks
    @ColumnInfo(name = "feed_network_pk")
    val feed_network_pk: Int?,

    @ColumnInfo(name = "network_id")
    val network_id: Int,

    @ColumnInfo(name = "user_id")
    val user_id: Int,

    @ColumnInfo(name = "story_picture")
    val story_picture: String,

    @ColumnInfo(name = "duration")
    val duration: String,

    @ColumnInfo(name = "created_at")
    val created_at: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "status")
    val status: String
) {
    override fun toString(): String {
        return "FeedCompanyStoryPicture NetworkID: $network_id, Uploader: $user_id, " +
                "Duration: $duration, CreatedAt: $created_at, Duration: $duration, " +
                "CreatedAt: $created_at"
    }
}