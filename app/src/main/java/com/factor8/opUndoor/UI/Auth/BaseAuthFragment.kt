package com.factor8.opUndoor.UI.Auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.factor8.opUndoor.ViewModel.Auth.AuthViewModel
import com.factor8.opUndoor.ViewModel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/* baseClass pattern: Because all the fragments inside AuthActivity are gonna be sharing a single ViewModel(AuthViewModel) I am creating a BaseAuthFragment
* and then every fragment(inside AuthActivity) will extend this DaggerFragment Class*/

abstract class BaseAuthFragment: DaggerFragment(){

    val TAG: String = "AppDebug"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

}















