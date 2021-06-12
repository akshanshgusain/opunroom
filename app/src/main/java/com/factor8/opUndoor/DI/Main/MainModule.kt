package com.factor8.opUndoor.DI.Main

import com.factor8.opUndoor.API.Main.OpUndoorMainService
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Persistence.FeedDao
import com.factor8.opUndoor.Repository.Main.AccountRepository
import com.factor8.opUndoor.Repository.Main.CameraRepository
import com.factor8.opUndoor.Repository.Main.ChatRepository
import com.factor8.opUndoor.Repository.Main.FeedRepository
import com.factor8.opUndoor.Session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideOpUndoorMainService(retrofitBuilder: Retrofit.Builder): OpUndoorMainService {
        return retrofitBuilder
            .build()
            .create(OpUndoorMainService::class.java)
    }


    @MainScope
    @Provides
    fun provideAccountRepository(
        sessionManager: SessionManager,
        opUndoorMainService: OpUndoorMainService,
        accountPropertiesDao: AccountPropertiesDao
    ): AccountRepository {
        return AccountRepository(opUndoorMainService, sessionManager, accountPropertiesDao)
    }

    @MainScope
    @Provides
    fun provideCameraRepository(
        sessionManager: SessionManager,
        opUndoorMainService: OpUndoorMainService
    ): CameraRepository {
        return CameraRepository(opUndoorMainService, sessionManager)
    }

    @MainScope
    @Provides
    fun provideChatRepository(
        sessionManager: SessionManager,
        opUndoorMainService: OpUndoorMainService
    ): ChatRepository {
        return ChatRepository(opUndoorMainService, sessionManager)
    }

    @MainScope
    @Provides
    fun provideFeedRepository(
        sessionManager: SessionManager,
        opUndoorMainService: OpUndoorMainService,
        accountPropertiesDao: AccountPropertiesDao,
        userFeedStoreDao: FeedDao
    ): FeedRepository {
        return FeedRepository(opUndoorMainService, sessionManager, accountPropertiesDao, userFeedStoreDao)
    }
}