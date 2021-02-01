package com.factor8.opUndoor.DI.Main

import com.factor8.opUndoor.API.Auth.OpUndoorAuthService
import com.factor8.opUndoor.API.Main.OpUndoorMainService
import com.factor8.opUndoor.DI.Auth.AuthScope
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Persistence.AuthTokenDao
import com.factor8.opUndoor.Repository.Auth.AuthRepository
import com.factor8.opUndoor.Repository.Main.AccountRepository
import com.factor8.opUndoor.Repository.Main.CameraRepository
import com.factor8.opUndoor.Repository.Main.ChatRepository
import com.factor8.opUndoor.Repository.Main.FeedRepository
import com.factor8.opUndoor.Session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule{

    @MainScope
    @Provides
    fun provideOpUndoorMainService(retrofitBuilder: Retrofit.Builder): OpUndoorMainService {
        return retrofitBuilder
                .build()
                .create(OpUndoorMainService::class.java)
    }

//    @AuthScope
//    @Provides
//    fun provideAuthRepository(sessionManager: SessionManager, authTokenDao: AuthTokenDao, accountPropertiesDao: AccountPropertiesDao,
//                              openApiAuthService: OpUndoorAuthService
//    ): AuthRepository {
//        return AuthRepository(authTokenDao, accountPropertiesDao, openApiAuthService, sessionManager)
//    }

    @MainScope
    @Provides
    fun provideAccountRepository(sessionManager: SessionManager, opUndoorMainService: OpUndoorMainService) : AccountRepository{
        return AccountRepository(opUndoorMainService, sessionManager)
    }

    @MainScope
    @Provides
    fun provideCameraRepository(sessionManager: SessionManager, opUndoorMainService: OpUndoorMainService) : CameraRepository{
        return CameraRepository(opUndoorMainService, sessionManager)
    }

    @MainScope
    @Provides
    fun provideChatRepository(sessionManager: SessionManager, opUndoorMainService: OpUndoorMainService) : ChatRepository{
        return ChatRepository(opUndoorMainService, sessionManager)
    }

    @MainScope
    @Provides
    fun provideFeedRepository(sessionManager: SessionManager, opUndoorMainService: OpUndoorMainService) : FeedRepository{
        return FeedRepository(opUndoorMainService, sessionManager)
    }
}