package com.factor8.opUndoor.ViewModel.Main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Repository.Main.AccountRepository
import com.factor8.opUndoor.Session.SessionManager
import com.factor8.opUndoor.UI.DataState
import com.factor8.opUndoor.UI.Loading
import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent
import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent.*
import com.factor8.opUndoor.UI.Main.Account.state.AccountViewState
import com.factor8.opUndoor.Util.AbsentLiveData
import com.factor8.opUndoor.ViewModel.BaseViewModel
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class AccountViewModel
@Inject
constructor(val accountRepository: AccountRepository, val sessionManager: SessionManager) : BaseViewModel<AccountStateEvent, AccountViewState>() {

    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        when (stateEvent) {
            is GetAccountPropertiesEvent -> {
                return sessionManager.cachedToken.value?.let {
                    Log.d(TAG, "handleStateEvent: " + it.id + " AccountViewModel")
                    accountRepository.getAccountProperties(it)
                } ?: AbsentLiveData.create()

            }

            is UpdateUserProfileEvent -> {
                return sessionManager.cachedToken.value?.let {

                    Log.d(TAG, "handleStateEvent: Updated Data: ${stateEvent} ")

                    val firstName = RequestBody.create(MediaType.parse("text/plain"), stateEvent.firstName)
                    val lastName = RequestBody.create(MediaType.parse("text/plain"), stateEvent.lastName)
                    val username = RequestBody.create(MediaType.parse("text/plain"), stateEvent.username)
                    val email = RequestBody.create(MediaType.parse("text/plain"), stateEvent.email)
                    val network = RequestBody.create(MediaType.parse("text/plain"), stateEvent.network)
                    val fieldOfWork = RequestBody.create(MediaType.parse("text/plain"), stateEvent.fieldOfWork)
                    val makeAccountPublic = RequestBody.create(MediaType.parse("text/plain"), stateEvent.makeAccountPublic)
                    val totalWorkExperience = RequestBody.create(MediaType.parse("text/plain"), stateEvent.totalWorkExperience)
                    val currentCompany = RequestBody.create(MediaType.parse("text/plain"), stateEvent.currentCompany)


                    accountRepository.updateUserProfile(
                            authToken = it,
                            firstName = firstName,
                            lastName = lastName,
                            username = username,
                            email = email,
                            network = network,
                            fieldOfWork = fieldOfWork,
                            makeAccountPublic = makeAccountPublic,
                            totalWorkExperience = totalWorkExperience,
                            currentCompany = currentCompany,
                            displayPicture = stateEvent.displayPicture,
                            coverPicture = stateEvent.coverPicture
                    )

                } ?: AbsentLiveData.create()
            }

            is None -> {
                return object : LiveData<DataState<AccountViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = DataState(null, Loading(false), null)
                    }
                }
            }
        }
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if (update.accountProperties == accountProperties) {
            return
        }
        update.accountProperties = accountProperties
        setViewState(update)
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

    fun cancelActiveJobs() {
        accountRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }


    fun handlePendingData() {
        setStateEvent(None())
    }

    fun logout() {
        sessionManager.logout()
    }


    fun setUpdatedProfileFields(
            firstName: String?,
            lastName: String?,
            currentCompany: String?,
            fieldOfWork: String?,
            totalWorkExperience: String?,
            email: String?,
            username: String?,
            makeAccountPublic: String?,
            displayPicture: Uri?,
            coverPicture: Uri?) {

        val update = getCurrentViewStateOrNew()
        val newProfileFields = update.updatedProfileFields

        firstName?.let {
            newProfileFields.firstName = it
        }
        lastName?.let {
            newProfileFields.LastName = it
        }

        currentCompany?.let {
            newProfileFields.currentCompany = it
        }

        fieldOfWork?.let {
            newProfileFields.fieldOfWork = it
        }
        totalWorkExperience?.let {
            newProfileFields.totalWorkExperience = it
        }

        email?.let {
            newProfileFields.email = it
        }
        username?.let {
            newProfileFields.username = it
        }
        makeAccountPublic?.let {
            newProfileFields.makeAccountPublic = it
        }
        if (displayPicture != null) {
            displayPicture?.let {
                newProfileFields.displayPicture = it
            }
        }
        if (coverPicture != null) {
            coverPicture?.let {
                newProfileFields.coverPicture = it
            }
        }


        update.updatedProfileFields = newProfileFields
        _viewState.value = update

    }

    fun clearNewBlogFields() {
        val update = getCurrentViewStateOrNew()
        update.updatedProfileFields = AccountViewState.UpdatedProfileFields()
        setViewState(update)
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}