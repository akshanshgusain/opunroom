package com.factor8.opUndoor.Repository.Auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.switchMap
import com.factor8.opUndoor.API.Auth.OpUndoorAuthService
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Persistence.AuthTokenDao
import com.factor8.opUndoor.Repository.NetworkBoundResource
import com.factor8.opUndoor.Session.SessionManager
import com.factor8.opUndoor.UI.Auth.State.AuthViewState
import com.factor8.opUndoor.UI.Auth.State.LoginFields
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Response
import com.factor8.opUndoor.UI.ResponseType
import com.factor8.opUndoor.Util.*
import com.factor8.opUndoor.Util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.factor8.opUndoor.Util.ErrorHandling.Companion.GENERIC_AUTH_ERROR
import kotlinx.coroutines.Job

class AuthRepository
constructor(
        val authTokenDao: AuthTokenDao,
        val accountPropertiesDao: AccountPropertiesDao,
        val opUndoorAuthService: OpUndoorAuthService,
        val sessionManager: SessionManager
) {
    private val TAG: String = "AppDebug"
    private var repositoryJob: Job? = null

    //Function for Login
    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        Log.e(TAG, "attemptLogin: email: ${email}, password: ${password}" )
        val loginFieldErrors = LoginFields(email, password).isValidForLogin()

        if (!loginFieldErrors.equals(LoginFields.LoginError.none())) {
            return returnErrorResponse(loginFieldErrors, ResponseType.Dialog())
        }

        return object : NetworkBoundResource<AccountProperties, AuthViewState>(
                sessionManager.isConnectedToTheInternet()
        ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<AccountProperties>) {
                Log.d(TAG, "handleApiSuccessResponse: ${response}")

                // Incorrect login credentials counts as a 200 response(success) from server, so need to handle that
                //At present the API does not have an default status message hence, I am gonna check whether the id is null
                if (response.body.id.equals(GENERIC_AUTH_ERROR)) {
                    //return onErrorReturn(response.body.errorMessage, true, false)
                    return onErrorReturn(Constants.INVALID_CREDENTIALS, true, false);
                }

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

    fun cancelActiveJobs() {
        Log.d(TAG, "AuthRepository: Cancelling on-going jobs...")
        repositoryJob?.cancel()
    }


}