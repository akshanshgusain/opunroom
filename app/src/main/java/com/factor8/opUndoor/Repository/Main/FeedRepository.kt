package com.factor8.opUndoor.Repository.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.factor8.opUndoor.API.Main.OpUndoorMainService
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Repository.JobManager
import com.factor8.opUndoor.Repository.NetworkBoundResource
import com.factor8.opUndoor.Session.SessionManager
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Main.Account.state.AccountViewState
import com.factor8.opUndoor.UI.Main.Feed.state.FeedStateEvent
import com.factor8.opUndoor.UI.Main.Feed.state.FeedViewState
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.Util.ApiSuccessResponse
import com.factor8.opUndoor.Util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class FeedRepository

constructor(
        var service: OpUndoorMainService,
        var sessionManager: SessionManager,
        val accountPropertiesDao: AccountPropertiesDao
): JobManager("FeedRepository"){

    private val TAG: String = "AppDebug"

    fun getAccountProperties(authToken: AuthToken): LiveData<DataState<FeedViewState>>{
            return object: NetworkBoundResource<AccountProperties, Any, FeedViewState>(
                    sessionManager.isConnectedToTheInternet(),
                    false,
                    true,
                    true
            ){
                override suspend fun createCacheRequestAndReturn() {
                    withContext(Dispatchers.Main){

                        //finish by view cache
                        result.addSource(loadFromCache()){
                            onCompleteJob(DataState.data(it,null))
                        }
                    }
                }

                override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<AccountProperties>) {
                    //updateLocalDb(response.body)

                   // createCacheRequestAndReturn()
                }

                override fun createCall(): LiveData<GenericApiResponse<AccountProperties>> {
                    return AbsentLiveData.create()
                }

                override fun loadFromCache(): LiveData<FeedViewState> {
                    return accountPropertiesDao.searchById(authToken.account_id!!)
                            .switchMap {
                                object: LiveData<FeedViewState>(){
                                    override fun onActive() {
                                        super.onActive()
                                        value = FeedViewState(authToken,it)
                                    }
                                }
                            }
                }

                override suspend fun updateLocalDb(cacheObject: Any?) {

                }

                override fun setJob(job: Job) {
                   addJob("getAccountProperties", job)
                }
            }.asLiveData()
    }


}