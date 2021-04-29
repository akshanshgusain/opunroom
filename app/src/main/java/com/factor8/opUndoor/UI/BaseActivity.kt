package com.factor8.opUndoor.UI

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.factor8.opUndoor.Session.SessionManager
import com.factor8.opUndoor.Util.Constants.Companion.PERMISSIONS_REQUEST_READ_STORAGE
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject


//Transmitting UI realated information from DataState to Here
abstract class BaseActivity: DaggerAppCompatActivity(),DataStateChangeListener, UICommunicationListener{


    val TAG: String = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var logger: HttpLoggingInterceptor

    override fun onUIMessageReceived(uiMessage: UIMessage) {
        when(uiMessage.uiMessageType){

            is UIMessageType.AreYouSureDialog -> {
                areYouSureDialog(
                        uiMessage.message,
                        uiMessage.uiMessageType.callback
                )
            }

            is UIMessageType.MakeAccountPublic ->{
                makeAccountPublic(
                        uiMessage.message,
                        uiMessage.uiMessageType.callback,
                        uiMessage.switchVal
                )
            }

            is UIMessageType.Toast -> {
                displayToast(uiMessage.message)
            }

            is UIMessageType.Dialog -> {
                displayInfoDialog(uiMessage.message)
            }

            is UIMessageType.None -> {
                Log.i(TAG, "onUIMessageReceived: ${uiMessage.message}")
            }
        }
    }


    override fun onDataStateChange(dataState: DataState<*>?) {
        dataState?.let{
            GlobalScope.launch(Dispatchers.Main){
                displayProgressBar(it.loading.isLoading)

                it.error?.let { errorEvent ->
                    handleStateError(errorEvent)
                }

                it.data?.let {
                    it.response?.let { responseEvent ->
                        handleStateResponse(responseEvent)
                    }
                }
            }
        }
    }


    abstract fun displayProgressBar(bool: Boolean)

    private fun handleStateResponse(event: Event<Response>){
        event.getContentIfNotHandled()?.let{

            when(it.responseType){
                is ResponseType.Toast ->{
                    it.message?.let{message ->
                        displayToast(message)
                    }
                }

                is ResponseType.Dialog ->{
                    it.message?.let{ message ->
                        displaySuccessDialog(message)
                    }
                }

                is ResponseType.None -> {
                    Log.i(TAG, "handleStateResponse: ${it.message}")
                }
            }

        }
    }

    private fun handleStateError(event: Event<StateError>){
        event.getContentIfNotHandled()?.let{
            when(it.response.responseType){
                is ResponseType.Toast ->{
                    it.response.message?.let{message ->
                        displayToast(message)
                    }
                }

                is ResponseType.Dialog ->{
                    it.response.message?.let{ message ->
                        displayErrorDialog(message)
                    }
                }

                is ResponseType.None -> {
                    Log. i(TAG, "handleStateError: ${it.response.message}")
                }
            }
        }
    }

    override fun isStoragePermissionGranted(): Boolean {
        if (
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  ) {


            ActivityCompat.requestPermissions(this,
                    arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PERMISSIONS_REQUEST_READ_STORAGE
            )

            return false
        } else {
            // Permission has already been granted
            return true
        }
    }

    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager
                    .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

}











