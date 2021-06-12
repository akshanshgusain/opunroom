package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_network")
data class FeedNetworkStore(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "feed_network_pk")
    val feed_network_pk: Int,

    @ColumnInfo(name = "feed_user_store_pk")
    var feed_user_store_pk: Int,

    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name="network")
    val network: String,

    @ColumnInfo(name= "profile_picture")
    val profile_picture: String,

    @ColumnInfo(name = "cover_picture")
    val cover_picture: String,

    @ColumnInfo(name = "website")
    val website: String,

    @ColumnInfo(name ="created_at")
    val created_at:String?

){
    override fun toString(): String {
        return "FeedNetwork: ID: $feed_network_pk, name: $name, network: $network"
    }
}
