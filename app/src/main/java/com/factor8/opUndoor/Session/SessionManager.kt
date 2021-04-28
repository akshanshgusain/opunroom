package com.factor8.opUndoor.Session


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.AuthToken
import com.factor8.opUndoor.Persistence.AccountPropertiesDao
import com.factor8.opUndoor.Persistence.AuthTokenDao
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager

@Inject
constructor(
        val authTokenDao: AuthTokenDao,
        val accountPropertiesDao: AccountPropertiesDao,
        val application: Application
) {

    private val TAG: String = "AppDebug"

    private val _cachedToken = MutableLiveData<AuthToken>()
    private val _cachedAccountProperties = MutableLiveData<AccountProperties>()

    val cachedToken: LiveData<AuthToken>
        get() = _cachedToken

    val cachedAccountProperties: LiveData<AccountProperties>
            get() = _cachedAccountProperties

    fun login(newValue: AuthToken) {
        setValue(newValue)
    }
    fun setAccountProps(newValue: AccountProperties){
        setValue2(accountProperties = newValue)
    }

    fun setValue(newValue: AuthToken?) {
        GlobalScope.launch(Dispatchers.Main) {
            if (_cachedToken.value != newValue) {
                _cachedToken.value = newValue
            }
        }
    }

    fun setValue2(accountProperties: AccountProperties?) {
        GlobalScope.launch(Dispatchers.Main) {
            if(_cachedAccountProperties.value != accountProperties){
                _cachedAccountProperties.value = accountProperties
            }
        }

        //Setting AccountProperties in DB on IO thread
        CoroutineScope(Dispatchers.IO).launch {
            try{
                _cachedAccountProperties.value?.let {
                    accountPropertiesDao.insertAndReplace(it)
                }
            }catch (e: Exception){
                Log.e(TAG, "Exception: storing account properties in DB ${e.toString()}" )
            }
        }
    }



    fun logout() {
        Log.d(TAG, "logout: ")

        CoroutineScope(Dispatchers.IO).launch {
            var errorMessage: String? = null
            try {
                _cachedToken.value!!.id?.let {
                    authTokenDao.nullifyToken(it)
                } ?: throw CancellationException("Token Error. Logging out user.")

            } catch (e: CancellationException) {
                Log.e(TAG, "logout: ${e.message}")
                errorMessage = e.message
            } catch (e: Exception) {
                Log.e(TAG, "logout: ${e.message}")
                errorMessage = errorMessage + "\n" + e.message
            } finally {
                errorMessage?.let {
                    Log.e(TAG, "logout: ${errorMessage}")
                }
                Log.d(TAG, "logout: finally")
                setValue(null)
                setValue2(null)
            }
        }
    }


    fun isConnectedToTheInternet(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}