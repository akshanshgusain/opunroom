package com.factor8.opUndoor.Repository.Auth

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.switchMap
import com.factor8.opUndoor.API.Auth.OpUndoorAuthService
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Persistence.AuthTokenDao
import com.factor8.opUndoor.Repository.JobManager
import com.factor8.opUndoor.Repository.NetworkBoundResource
import com.factor8.opUndoor.Session.SessionManager
import com.factor8.opUndoor.UI.Auth.State.AuthViewState
import com.factor8.opUndoor.UI.Auth.State.LoginFields
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Response
import com.factor8.opUndoor.UI.ResponseType
import com.factor8.opUndoor.Util.*
import com.factor8.opUndoor.Util.ErrorHandling.Companion.ERROR_SAVE_AUTH_TOKEN
import com.factor8.opUndoor.Util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.factor8.opUndoor.Util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import com.factor8.opUndoor.Util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import kotlinx.coroutines.Job

class AuthRepository
constructor(
        val authTokenDao: AuthTokenDao,
        val accountPropertiesDao: AccountPropertiesDao,
        val opUndoorAuthService: OpUndoorAuthService,
        val sessionManager: SessionManager,
        val sharedPreferences: SharedPreferences,
        val sharedPrefsEditor: SharedPreferences.Editor
): JobManager("AuthRepository") {

    private val TAG: String = "AppDebug"
    private var repositoryJob: Job? = null

    //Function for Login
    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        Log.e(TAG, "attemptLogin: email: ${email}, password: ${password}" )
        val loginFieldErrors = LoginFields(email, password).isValidForLogin()

        if (!loginFieldErrors.equals(LoginFields.LoginError.none())) {
            return returnErrorResponse(loginFieldErrors, ResponseType.Dialog())
        }

        return object : NetworkBoundResource<AccountProperties, Any,AuthViewState>(
                sessionManager.isConnectedToTheInternet(),
                true,
                true,
                false
        ) {

            override fun loadFromCache(): LiveData<AuthViewState> {
                return AbsentLiveData.create()
            }

            override suspend fun updateLocalDb(cacheObject: Any?) {

            }

            override suspend fun createCacheRequestAndReturn() {

            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<AccountProperties>) {
                Log.d(TAG, "handleApiSuccessResponse: ${response}")

                // Incorrect login credentials counts as a 200 response(success) from server, so need to handle that
                //At present the API does not have an default status message hence, I am gonna check whether the id is null
                if (response.body.id.equals(GENERIC_AUTH_ERROR)) {
                    //return onErrorReturn(response.body.errorMessage, true, false)
                    return onErrorReturn(Constants.INVALID_CREDENTIALS, true, false);
                }

                // Don't care about result here. Just insert if it doesn't exist b/c of foreign key relationship
                // with AuthToken
                accountPropertiesDao.insertOrIgnore(
                        AccountProperties(
                                response.body.id,
                                response.body.f_name,
                                response.body.l_name,
                                response.body.username,
                                response.body.email,
                                response.body.profile_picture,
                                response.body.network,
                                response.body.profession,
                                response.body.experience,
                                response.body.is_verified,
                                response.body.cover_picture,
                                response.body.privacy,
                                response.body.network_profile_picture,
                                response.body.network_cover_picture,
                                response.body.current_company
                        )
                )

                // will return -1 if failure
                val result = authTokenDao.insert(
                        AuthToken(
                                response.body.id,
                                response.body.id
                        )
                )
                if(result < 0){
                    return onCompleteJob(DataState.error(
                            Response(ERROR_SAVE_AUTH_TOKEN, ResponseType.Dialog()))
                    )
                }

                saveAuthenticatedUserToPrefs(response.body.email)

                onCompleteJob(
                        DataState.data(
                                data = AuthViewState(
                                        authToken = AuthToken(response.body.id, response.body.id), accountProperties = response.body
                                )
                        )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<AccountProperties>> {
                return opUndoorAuthService.login(email, password)
            }

            override fun setJob(job: Job) {
                repositoryJob?.cancel()
                repositoryJob = job
            }

        }.asLiveData()
    }

    fun checkPreviousAuthUser(): LiveData<DataState<AuthViewState>>{

        val previousAuthUserEmail: String? = sharedPreferences.getString(PreferenceKeys.PREVIOUS_AUTH_USER, null)

        if(previousAuthUserEmail.isNullOrBlank()){
            Log.d(TAG, "checkPreviousAuthUser: No previously authenticated user found.")
            return returnNoTokenFound()
        }
        else{
            return object: NetworkBoundResource<Void, Any, AuthViewState>(
                sessionManager.isConnectedToTheInternet(),
                false,
                false,
                false
            ){

                // Ignore
                override fun loadFromCache(): LiveData<AuthViewState> {
                    return AbsentLiveData.create()
                }

                // Ignore
                override suspend fun updateLocalDb(cacheObject: Any?) {

                }

                override suspend fun createCacheRequestAndReturn() {
                    accountPropertiesDao.searchByEmail(previousAuthUserEmail).let { accountProperties ->
                        Log.d(TAG, "createCacheRequestAndReturn: searching for token... account properties: ${accountProperties}")

                        accountProperties?.let {
                            if(accountProperties.id > -1){
                                authTokenDao.searchByPk(accountProperties.id).let { authToken ->
                                    if(authToken != null){
                                        if(authToken.id != null){
                                            onCompleteJob(
                                                DataState.data(
                                                    AuthViewState(authToken = authToken)
                                                )
                                            )
                                            return
                                        }
                                    }
                                }
                            }
                        }
                        Log.d(TAG, "createCacheRequestAndReturn: AuthToken not found...")
                        onCompleteJob(
                            DataState.data(
                                null,
                                Response(
                                    RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE,
                                    ResponseType.None()
                                )
                            )
                        )
                    }
                }

                // not used in this case
                override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Void>) {
                }

                // not used in this case
                override fun createCall(): LiveData<GenericApiResponse<Void>> {
                    return AbsentLiveData.create()
                }

                override fun setJob(job: Job) {
                    addJob("checkPreviousAuthUser", job)
                }


            }.asLiveData()
        }
    }

    private fun returnNoTokenFound(): LiveData<DataState<AuthViewState>>{
        return object: LiveData<DataState<AuthViewState>>(){
            override fun onActive() {
                super.onActive()
                value = DataState.data(null, Response(RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE, ResponseType.None()))
            }
        }
    }

    private fun saveAuthenticatedUserToPrefs(email: String) {
        sharedPrefsEditor.putString(PreferenceKeys.PREVIOUS_AUTH_USER, email)
        sharedPrefsEditor.apply()
    }

    private fun returnErrorResponse(errorMessage: String, responseType: ResponseType): LiveData<DataState<AuthViewState>> {
        Log.d(TAG, "returnErrorResponse: ${errorMessage}")

        return object : LiveData<DataState<AuthViewState>>() {
            override fun onActive() {
                super.onActive()
                value = DataState.error(
                        Response(
                                errorMessage,
                                responseType
                        )
                )
            }
        }
    }

//    fun cancelActiveJobs() {
//        Log.d(TAG, "AuthRepository: Cancelling on-going jobs...")
//        repositoryJob?.cancel()
//    }


}