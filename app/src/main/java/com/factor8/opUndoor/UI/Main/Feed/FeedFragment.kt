package com.factor8.opUndoor.UI.Main.Feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.factor8.opUndoor.Models.AccountProperties
import com.factor8.opUndoor.Models.Relationships.FeedCompanyToFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedFriendStoreToFeedStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedGroupStoreToFeedGroupStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedNetworkStoreAndFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Wrappers.FeedLoadFromCache
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Main.Account.state.AccountStateEvent
import com.factor8.opUndoor.UI.Main.Feed.state.FeedStateEvent
import com.factor8.opUndoor.UI.Main.ViewPagerChangeListener
import com.factor8.opUndoor.UI.UICommunicationListener
import com.factor8.opUndoor.UI.UIMessage
import com.factor8.opUndoor.UI.UIMessageType
import com.factor8.opUndoor.Util.Constants
import com.factor8.opUndoor.Util.Constants.Companion.NO_STORIES
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlin.math.log


class FeedFragment : BaseFeedFragment(),
FeedFriendListAdapter.FriendInteraction,
    FeedGroupListAdapter.GroupInteraction,
    FeedCompanyListAdapter.CompanyInteraction,
    FeedNetworkListAdapter.NetworkInteraction {

    lateinit var viewPagerChangerListener: ViewPagerChangeListener
    lateinit var recyclerAdapter: FeedListAdapter

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
        textView_name.setOnClickListener{
            navigateToAccountFragment()
        }
        initRecyclerView()
        subscribeObservers();

    }
    
    

    private fun navigateToCameraFragment() {
        viewPagerChangerListener.changeViewPagerPositionTo(Constants.CAMERA_FRAGMENT_POSITION)
    }

    private fun navigateToAccountFragment() {
        findNavController().navigate(R.id.action_viewPagerContainer_to_accountFragment)
    }

    private fun navigateToViewStoryFragment() {
        findNavController().navigate(R.id.action_viewPagerContainer_to_viewStoryFragment)
    }


    private fun subscribeObservers() {
                viewModel.dataState.observe(viewLifecycleOwner, Observer { datastate ->
                    stateChangeListener.onDataStateChange(datastate)
                    if (datastate != null) {
                        datastate.data?.let { data ->
                            data.data?.let { event ->
                                event.getContentIfNotHandled()?.let { feedViewState ->
                                    Log.d(TAG, "subscribeObservers: ${feedViewState}")
                                    feedViewState.accountProperties?.let { accountProperties ->
                                        Log.d(TAG, "subscribeObservers: FeedFragment: ${accountProperties.f_name} ${accountProperties.l_name}")
                                        viewModel.setAccountPropertiesData(accountProperties)
                                    }
                                    feedViewState.feedLoadFromCache?.let{
                                        viewModel.setFeedLoadFromCache(it)
                                    }
                                }
                            }
                        }

                    }
                })

                viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
                    if(viewState != null){
                        viewState.accountProperties?.let {
                            Log.d(TAG, "subscribeObservers: FeedFragment: ${it.f_name} ${it.l_name}")
                            setAccountDetails(it)
                        }
                        viewState.feedLoadFromCache?.let{
                            recyclerAdapter.apply {
                                submitList(it)
                            }
                        }
                    }
                })
    }


    private fun initRecyclerView(){
        main_recyclerView.apply{
            layoutManager = LinearLayoutManager(this@FeedFragment.context)
            recyclerAdapter = FeedListAdapter(
                requestManager = requestManager,
                context = requireActivity(),
                interaction = this@FeedFragment
            )

            adapter = recyclerAdapter
        }
    }


    private fun setAccountDetails(accountProperties: AccountProperties){
            val fullName = "Hi, ${accountProperties.f_name} ${accountProperties.l_name}"
            textView_name.text = fullName
            requestManager.load(Constants.PROFILE_IMAGES+accountProperties.profile_picture).into(imageView_dp)

//            //once the profile is loaded get the feed
//            viewModel.setStateEvent(FeedStateEvent.GetFeedEvent)
    }

    override fun onResume() {
        super.onResume()
        //call to get account properties from View model
        viewModel.setStateEvent(FeedStateEvent.GetFeedEvent)
    }

    override fun onFriendSelected(position: Int, item: FeedFriendStoreToFeedStoryPicture) {
        if(position == 0){
            navigateToCameraFragment()
        }else{
            if(item.storyPictures.isNotEmpty()){
                viewModel.setStoryList(Constants.STORY_TYPE_FRIEND_STORY, item)
                navigateToViewStoryFragment()
            }else{
                uiCommunicationListener.onUIMessageReceived(
                    UIMessage(NO_STORIES, uiMessageType = UIMessageType.Toast())
                )
            }
        }
    }

    override fun onGroupSelected(position: Int, item: FeedGroupStoreToFeedGroupStoryPicture) {
        if(position == 0){
            //Create a group

        }else{
            if(item.groupStoryPictures.isNotEmpty()){
                viewModel.setStoryList(Constants.STORY_TYPE_GROUP_STORY, item)
                navigateToViewStoryFragment()
            }else{
                uiCommunicationListener.onUIMessageReceived(
                    UIMessage(NO_STORIES, uiMessageType = UIMessageType.Toast())
                )
            }
        }
    }

    override fun onCompanySelected(position: Int, item: FeedCompanyToFeedCompanyStoryPicture) {
        Log.d(TAG, "onCompanySelected: ${position}, item : ${item.feedCompany.company_name}")

        if(item.companyStories.isNotEmpty()){
            viewModel.setStoryList(Constants.STORY_TYPE_COMPANY_STORY, item)
            navigateToViewStoryFragment()
        }else{
            uiCommunicationListener.onUIMessageReceived(
                UIMessage(NO_STORIES, uiMessageType = UIMessageType.Toast())
            )
        }
    }

    override fun onNetworkSelected(
        position: Int,
        item: FeedNetworkStoreAndFeedCompanyStoryPicture
    ) {
        Log.d(TAG, "onNetworkSelected: ${position}, item : ${item.feedNetworkStore.name}")

        if(item.companyStoryPicture.isNotEmpty()){
            viewModel.setStoryList(Constants.STORY_TYPE_NETWORK_STORY, item)
            navigateToViewStoryFragment()
        }else{
            uiCommunicationListener.onUIMessageReceived(
                UIMessage(NO_STORIES, uiMessageType = UIMessageType.Toast())
            )
        }
    }
}