package com.factor8.opUndoor.Persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.factor8.opUndoor.Models.AuthToken

@Dao
interface AuthTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authTokenDao: AuthToken): Long

    @Query("UPDATE auth_token SET id = null WHERE account_id = :id")
    fun nullifyToken(id: Int): Int
}