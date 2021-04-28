package com.factor8.opUndoor.UI.Main.Account

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent
import com.factor8.opUndoor.UI.Main.Account.state.AccountViewState
import com.factor8.opUndoor.Util.Constants
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : BaseAccountFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView_back.setOnClickListener{
            findNavController().navigateUp()
        }

        imageView_settings.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_accountSettingsFragment)
        }

        button_logout.setOnClickListener {
            viewModel.logout()
        }

        subscribeObservers();
    }


    private fun subscribeObservers(){
            viewModel.dataState.observe(viewLifecycleOwner, Observer { datastate ->
                stateChangeListener.onDataStateChange(datastate)
                if (datastate != null) {
                    datastate.data?.let { data ->
                        data.data?.let { event ->
                            event.getContentIfNotHandled()?.let { viewState ->
                                viewState.accountProperties?.let { accountProperties ->
                                    //Log.d(TAG, "Account Fragment, DataState : f_name: ${accountProperties.f_name} and l_name: ${accountProperties.l_name}")
                                    viewModel.setAccountPropertiesData(accountProperties)
                                }
                            }

                        }
                    }
                }
            })

            viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
                if (viewState != null) {
                    viewState.accountProperties?.let {
                        //Log.d(TAG, "View State: f_name: ${it.f_name},  l_name: ${it.l_name},  coverpicture: ${it.cover_picture}")
                        setAccountDataFields(it)
                    }
                }
            })
    }

    private fun setAccountDataFields(accountProperties: AccountProperties){
        val fullname = accountProperties.f_name +" "+accountProperties.l_name
        textView_name?.setText(fullname)
        textView_username?.setText(accountProperties.username)
        textView_profession?.setText(accountProperties.profession)
        textView_experience?.setText(accountProperties.experience)
        requestManager.load(Constants.PROFILE_IMAGES+accountProperties.profile_picture).into(imageView_dp)
        requestManager.load(Constants.PROFILE_IMAGES+accountProperties.cover_picture).into(imageView_cover)

        requestManager.load(Constants.COMPANY_IMAGES+accountProperties.network_cover_picture).into(imageView_banner_image)
        requestManager.load(Constants.COMPANY_IMAGES+accountProperties.network_profile_picture).into(imageView_company_dp)
        textView_comapny_full_name?.setText(accountProperties.network)
    }

    override fun onResume() {
        super.onResume()

        //call to get account properties from View model
        viewModel.setStateEvent(AccountStateEvent.GetAccountPropertiesEvent())
    }
}