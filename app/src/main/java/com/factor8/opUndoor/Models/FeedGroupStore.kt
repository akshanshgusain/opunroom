package com.factor8.opUndoor.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_group_store")
data class FeedGroupStore(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="feed_group_store_pk")
    val feed_group_store_pk: Int,

    // feed_user_store_pk is the primary key of FeedUserStore class which holds info of current user
    @ColumnInfo(name = "feed_user_store_pk")
    val feed_user_store_pk: Int,

    @ColumnInfo(name = "group_admin_id")
    val group_admin_id: Int,

    @ColumnInfo(name = "group_title")
    val group_title: String,

    @ColumnInfo(name = "group_user_ids")
    val group_user_ids: String,

    @ColumnInfo(name = "number_of_members")
    val number_of_members: Int,

    @ColumnInfo(name = "admin_profile_picture")
    val admin_profile_picture: String,

    @ColumnInfo(name = "admin_name")
    val admin_name: String
){
    override fun toString(): String {
        return "FeedGroupStore, Group Id: $group_admin_id, Group Admin: $admin_name"
    }
}
