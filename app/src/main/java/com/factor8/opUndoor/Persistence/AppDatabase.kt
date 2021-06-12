package com.factor8.opUndoor.Persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.factor8.opUndoor.Models.*

@Database(
    entities = [
        AuthToken::class,
        AccountProperties::class,
        FeedUserStore::class,
        FeedStoryPicture::class,
        FeedCompany::class,
        FeedCompanyStoryPicture::class,
        FeedFriendStore::class,
        FeedGroupStore::class,
        FeedGroupStoryPicture::class,
        FeedNetworkStore::class,
        FeedNewsStore::class,
        FeedNewsStoryPicture::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun accountPropertiesDao(): AccountPropertiesDao

    abstract fun userFeedStoreDao(): FeedDao

    companion object {
        const val DATABASE_NAME = "opUndoor_dp"
    }
}