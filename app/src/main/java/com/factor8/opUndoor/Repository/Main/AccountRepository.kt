package com.factor8.opUndoor.Repository.Main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.factor8.opUndoor.API.Main.OpUndoorMainService
import com.factor8.opUndoor.API.Main.Responses.UpdatedUserProfileResponse
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Repository.JobManager
import com.factor8.opUndoor.Repository.NetworkBoundResource
import com.factor8.opUndoor.Session.SessionManager
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Main.Account.state.AccountViewState
import com.factor8.opUndoor.UI.Response
import com.factor8.opUndoor.UI.ResponseType
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.Util.ApiSuccessResponse
import com.factor8.opUndoor.Util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AccountRepository
constructor(
        var service: OpUndoorMainService,
        var sessionManager: SessionManager,
        val accountPropertiesDao: AccountPropertiesDao
): JobManager("AccountRepository"){
    private val TAG: String = "AppDebug"
    private var repositoryJob: Job? = null

    fun getAccountProperties(authToken: AuthToken): LiveData<DataState<AccountViewState>>{
            return object: NetworkBoundResource<AccountProperties, AccountProperties, AccountViewState>(
                    sessionManager.isConnectedToTheInternet(),
                    false,
                    true,
                        true
            ){
                //If the network is down, view the cache and return
                override suspend fun createCacheRequestAndReturn() {
                    withContext(Dispatchers.Main){

                        //finish by viewing db cache
                        result.addSource(loadFromCache()){
                            onCompleteJob(DataState.data(it, null))
                        }
                    }
                }

                override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<AccountProperties>) {
                    updateLocalDb(response.body)

                    createCacheRequestAndReturn()
                }

                override fun loadFromCache(): LiveData<AccountViewState> {
                    return accountPropertiesDao.searchById(authToken.account_id!!)
                            .switchMap{
                                object: LiveData<AccountViewState>(){
                                    override fun onActive() {
                                        super.onActive()
                                        value = AccountViewState(authToken,it)
                                    }
                                }
                            }
                }

                override suspend fun updateLocalDb(cacheObject: AccountProperties?) {
//                    cacheObject?.let{
//                         accountPropertiesDao.updateAccountProperties(
//                                 cacheObject.id,
//                                 cacheObject.email,
//                                 cacheObject.username
//                         )
//                    }
                }

                override fun createCall(): LiveData<GenericApiResponse<AccountProperties>> {
                    //Not a network request
                    return AbsentLiveData.create()
                }

                override fun setJob(job: Job) {
                    addJob("getAccountProperties", job)
                }
            }.asLiveData()
    }

    fun updateUserProfile(
            authToken: AuthToken,
            firstName: RequestBody,
            lastName: RequestBody,
            currentCompany: RequestBody,
            fieldOfWork: RequestBody,
            network:RequestBody,
            totalWorkExperience: RequestBody,
            email: RequestBody,
            username: RequestBody,
            makeAccountPublic: RequestBody,
            displayPicture: MultipartBody.Part?,
            coverPicture: MultipartBody.Part?
    ): LiveData<DataState<AccountViewState>>{
        return object:
            NetworkBoundResource<UpdatedUserProfileResponse, AccountProperties, AccountViewState>(
                    sessionManager.isConnectedToTheInternet(),
                    true,
                    true,
                    false
            ){
            //not applicable
            override suspend fun createCacheRequestAndReturn() {

            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<UpdatedUserProfileResponse>) {
                Log.d(TAG, "handleApiSuccessResponse: UpdateUserProfile ${response.body} ")
            }

            override fun createCall(): LiveData<GenericApiResponse<UpdatedUserProfileResponse>> {
                return service.updateUserProfile(
                        id = authToken.account_id.toString(),
                        f_name = firstName,
                        l_name = lastName,
                        username = username,
                        email = email,
                        network = network,
                        profession = fieldOfWork,
                        privacy = makeAccountPublic,
                        experience = totalWorkExperience,
                        current_company = currentCompany

                )
            }

            override fun loadFromCache(): LiveData<AccountViewState> {
                return AbsentLiveData.create()
            }

            override suspend fun updateLocalDb(cacheObject: AccountProperties?) {

            }

            override fun setJob(job: Job) {
                addJob("updateUserProfile", job)
            }


        }.asLiveData()
    }
//    fun cancelActiveJobs() {
//        Log.d(TAG, "AccountRepository: Cancelling on-going jobs...")
//        repositoryJob?.cancel()
//    }
}