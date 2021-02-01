package com.factor8.opUndoor.DI.Auth


import com.factor8.opUndoor.API.Auth.OpUndoorAuthService
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Persistence.AuthTokenDao
import com.factor8.opUndoor.Repository.Auth.AuthRepository
import com.factor8.opUndoor.Session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule{

    @AuthScope
    @Provides
    fun provideOpUndoorAuthApiService(retrofitBuilder: Retrofit.Builder): OpUndoorAuthService {
        return retrofitBuilder
                .build()
                .create(OpUndoorAuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(sessionManager: SessionManager, authTokenDao: AuthTokenDao, accountPropertiesDao: AccountPropertiesDao,
                              openApiAuthService: OpUndoorAuthService
        ): AuthRepository {
        return AuthRepository(authTokenDao, accountPropertiesDao, openApiAuthService, sessionManager)
    }

}