package com.factor8.opUndoor.Persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.factor8.opUndoor.API.Models.AccountProperties
import com.factor8.opUndoor.API.Models.AuthToken

@Database(entities = [AuthToken::class, AccountProperties::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

 abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountProperties(): AccountPropertiesDao

    companion object{
        const val DATABASE_NAME = "opUndoor_dp"
    }
}