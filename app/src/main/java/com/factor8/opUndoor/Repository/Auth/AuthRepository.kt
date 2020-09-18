package com.factor8.opUndoor.Repository.Auth

import com.factor8.opUndoor.API.OpUndoorAuthService
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Persistence.AuthTokenDao
import com.factor8.opUndoor.Session.SessionManager

class AuthRepository
constructor(
        val authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        opUndoorAuthService: OpUndoorAuthService,
        sessionManager: SessionManager
){

}