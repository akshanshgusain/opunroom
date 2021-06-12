package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_news_store")
data class FeedNewsStore(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_news_store_pk")
    val feed_news_store_pk: Int,

    @ColumnInfo(name = "company_name")
    val company_name:String
){
    override fun toString(): String {
        return "FeedNewsStore Name: $company_name"
    }

}
