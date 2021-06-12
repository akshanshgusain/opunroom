package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_company")
data class FeedCompany(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_company_pk")
    val feed_company_pk: Int,

    // feed_user_store_pk is the primary key of FeedUserStore class which holds info of current user
    @ColumnInfo(name = "feed_user_store_pk")
    val feed_user_store_pk: Int,


    @ColumnInfo(name = "company_name")
    val company_name: String,

    @ColumnInfo(name = "network_name")
    val network_name: String,

    @ColumnInfo(name = "display_picture")
    val display_picture: String,

    @ColumnInfo(name = "cover_picture")
    val cover_picture: String,

    @ColumnInfo(name = "website")
    val website: String,

    @ColumnInfo(name = "created_at")
    val created_at: String?

) {
    override fun toString(): String {
        return "FeedCompany Name: $company_name"
    }
}