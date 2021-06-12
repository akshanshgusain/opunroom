package com.factor8.opUndoor.Persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.factor8.opUndoor.Models.AccountProperties

@Dao
interface AccountPropertiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(accountProperties: AccountProperties): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountProperties: AccountProperties): Long

    @Query("SELECT * FROM account_properties WHERE id = :id")
    fun searchById(id: Int): LiveData<AccountProperties>

    @Query("SELECT * FROM account_properties WHERE email = :email")
    suspend fun searchByEmail(email: String): AccountProperties?

    @Query("UPDATE account_properties SET email = :email, username = :username WHERE id = :pk")
    suspend fun updateAccountProperties(pk: Int, email: String, username: String)

}