package com.factor8.opUndoor.Repository.Main

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
            images: List<MultipartBody.Part?>
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
                val responseBody = response.body
                val updatedAccountProperties = AccountProperties(
                        id = responseBody.id,
                        f_name = responseBody.f_name,
                        l_name = responseBody.l_name,
                        username = responseBody.username,
                        email = responseBody.email,
                        profile_picture = responseBody.picture,
                        network = responseBody.network,
                        profession = responseBody.profession,
                        experience = responseBody.experience,
                        is_verified = responseBody.is_verified,
                        cover_picture = responseBody.coverpic,
                        privacy = responseBody.privacy,
                        network_cover_picture = sessionManager.accountPropertiesDao.searchByEmail(responseBody.email)!!.network_cover_picture,
                        network_profile_picture = sessionManager.accountPropertiesDao.searchByEmail(responseBody.email)!!.network_profile_picture,
                        current_company = responseBody.current_company
                )
                updateLocalDb(updatedAccountProperties)

            }

            override fun createCall(): LiveData<GenericApiResponse<UpdatedUserProfileResponse>> {
                return service.updateUserProfile(
                        id = RequestBody.create(MediaType.parse("text/plain"), authToken.account_id.toString()),
                        f_name = firstName,
                        l_name = lastName,
                        username = username,
                        email = email,
                        network = network,
                        profession = fieldOfWork,
                        privacy = makeAccountPublic,
                        experience = totalWorkExperience,
                        current_company = currentCompany,
                        image = images
                )
            }

            override fun loadFromCache(): LiveData<AccountViewState> {
                return AbsentLiveData.create()
            }

            override suspend fun updateLocalDb(cacheObject: AccountProperties?) {
                cacheObject?.let {
                    accountPropertiesDao.insertAndReplace(it)

                    //Once the data is saved in the cache call onComplete to complete the onGoing Networkbound job
                    withContext(Dispatchers.Main){
                        onCompleteJob(DataState.data(
                                null,
                        Response(
                                "Changes Saved",
                                ResponseType.Toast()
                        )))
                    }
                }
            }

            override fun setJob(job: Job) {
                addJob("updateUserProfile", job)
            }


        }.asLiveData()
    }

}