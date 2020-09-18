package com.factor8.opUndoor.Session


import android.app.Application
import com.factor8.opUndoor.Persistence.AuthTokenDao

class SessionManager

constructor(
        val authTokenDao: AuthTokenDao,
        val application: Application
) {

}