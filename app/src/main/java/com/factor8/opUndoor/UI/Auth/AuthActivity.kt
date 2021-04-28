package com.factor8.opUndoor.UI.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Auth.State.AuthViewState
import com.factor8.opUndoor.UI.BaseActivity
import com.factor8.opUndoor.UI.Main.MainActivity
import com.factor8.opUndoor.UI.ResponseType
import com.factor8.opUndoor.ViewModel.Auth.AuthViewModel
import com.factor8.opUndoor.ViewModel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), NavController.OnDestinationChangedListener{

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        findNavController(R.id.auth_nav_host_fragment).addOnDestinationChangedListener(this)

        subscribeObservers()
    }


    private fun subscribeObservers(){
        viewModel.dataState.observe(this, Observer { dataState ->

            //Passing dataState to BaseActivity VIA DataStateChangeListener
            onDataStateChange(dataState)

            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {authViewState->
                        authViewState.authToken?.let {
                            Log.d(TAG, "AuthToken: ${it.id}")
                            viewModel.setAuthToken(it)
                        }
                        authViewState.accountProperties?.let{
                            Log.d(TAG, "Account Properties of : ${it.f_name}")
                            viewModel.setAccountProperties(it)
                        }
                    }
                }// end of dataState.data
            }
        })

        viewModel.viewState.observe(this, Observer{
            Log.d(TAG, "AuthActivity, subscribeObservers: AuthViewState: ${it}")
            it.authToken?.let{
                sessionManager.login(it)
            }


            it.accountProperties?.let{
                //Save Account Properties to DB here
                sessionManager.setAccountProps(it)
            }
        })

        sessionManager.cachedToken.observe(this, Observer{ dataState ->
            Log.d(TAG, "AuthActivity, subscribeObservers: AuthDataState: ${dataState}")
            dataState.let{ authToken ->
                if(authToken != null && authToken.account_id != -1 && authToken.id != null){
                    navMainActivity()
                }
            }
        })
    }

    fun navMainActivity(){
        Log.d(TAG, "navMainActivity: called.")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //If the user moves away from the fragment We need to cancel any or all ongoing jobs
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
            viewModel.cancelActiveJobs();
    }

    override fun displayProgressBar(bool: Boolean) {
        if(bool){
            progress_bar.visibility = View.VISIBLE
        }
        else{
            progress_bar.visibility = View.GONE
        }
    }

}