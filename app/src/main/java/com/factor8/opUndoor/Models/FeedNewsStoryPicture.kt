package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_news_story_picture")
data class FeedNewsStoryPicture(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_news_story_picture_pk")
    val feed_news_story_picture_pk: Int,

    // feed_news_store_pk is the pk of the FeedNewsStore that holds value of all the New Stores
    @ColumnInfo(name = "feed_news_store_pk")
    val feed_news_store_pk: Int,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name="description")
    val description: String?,

    @ColumnInfo(name= "published_date")
    val published_date: String?,

    @ColumnInfo(name = "guid")
    val guid: String?,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "company")
    val company: String?
){
    override fun toString(): String {
        return "FeedNewsStoryPicture Company: $company, Title: $title"
    }
}
