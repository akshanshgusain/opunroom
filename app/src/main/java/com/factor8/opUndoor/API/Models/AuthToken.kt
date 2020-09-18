package com.factor8.opUndoor.API.Models

import android.accounts.Account
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/* At the time of writing of this code the server does not Uses the user token pattern, so we will user "id" as user token(which is redundant) but,
* once the server conforms to token pattern store the user token here in place of id.
*
* AuthToken table has a foreignKey relationship with AccountProperties Table (AuthToken table is the child of AccountProperties Table)  */

@Entity(
        tableName = "auth_token",
        foreignKeys = [
            ForeignKey(
                    entity = AccountProperties::class,
                    parentColumns = ["id"],
                    childColumns = ["account_id"],
                    onDelete = CASCADE
            )
        ]
)
data class AuthToken(


         @PrimaryKey
         @ColumnInfo(name = "account_id")
         var account_id: Int? = -1,

         @SerializedName("id")
         @Expose
         @ColumnInfo(name="id")
         var id: Int? = null
)