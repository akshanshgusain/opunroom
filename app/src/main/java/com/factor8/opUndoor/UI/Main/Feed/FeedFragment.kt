package com.factor8.opUndoor.UI.Main.Feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent
import com.factor8.opUndoor.UI.Main.Feed.state.FeedStateEvent
import com.factor8.opUndoor.UI.Main.ViewPagerChangeListener
import com.factor8.opUndoor.Util.Constants
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : BaseFeedFragment() {

    lateinit var viewPagerChangerListener: ViewPagerChangeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            viewPagerChangerListener = context as ViewPagerChangeListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView_cancel_button.setOnClickListener {
            navigateToCameraFragment()
        }

        imageView_dp.setOnClickListener {
            navigateToAccountFragment()
        }

        subscribeObservers();
    }

    private fun navigateToCameraFragment() {
        viewPagerChangerListener.changeViewPagerPositionTo(Constants.CAMERA_FRAGMENT_POSITION)
    }

    private fun navigateToAccountFragment() {
        findNavController().navigate(R.id.action_viewPagerContainer_to_accountFragment)
    }

    private fun subscribeObservers() {
                viewModel.dataState.observe(viewLifecycleOwner, Observer { datastate ->
                    stateChangeListener.onDataStateChange(datastate)
                    if (datastate != null) {
                        datastate.data?.let { data ->
                            data.data?.let { event ->
                                event.getContentIfNotHandled()?.let { feedViewState ->
                                    feedViewState.accountProperties?.let { accountProperties ->
                                        viewModel.setAccountPropertiesData(accountProperties)
                                    }
                                }
                            }
                        }
                    }
                })

                viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
                    if(viewState != null){
                        viewState.accountProperties?.let {
                            setAccountDetails(it)
                        }
                    }
                })
    }


    private fun setAccountDetails(accountProperties: AccountProperties){
            val fullName = "Hi, ${accountProperties.f_name} ${accountProperties.l_name}"
            textView_name.text = fullName
            requestManager.load(Constants.PROFILE_IMAGES+accountProperties.profile_picture).into(imageView_dp)
    }

    override fun onResume() {
        super.onResume()
        //call to get account properties from View model
        viewModel.setStateEvent(FeedStateEvent.GetAccountPropertiesEvent())

        //call to get Feed information from View model

    }
}