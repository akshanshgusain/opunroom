package com.factor8.opUndoor.UI.Main.Feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.factor8.opUndoor.Models.Relationships.FeedFriendStoreToFeedStoryPicture
import com.factor8.opUndoor.Models.Wrappers.FeedLoadFromCache
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Main.Feed.ListAdapters.FeedGroupAddParticipantsAdapter
import com.factor8.opUndoor.UI.Main.Feed.ListAdapters.FeedListAdapter
import com.factor8.opUndoor.UI.Main.Feed.state.FeedStateEvent
import kotlinx.android.synthetic.main.fragment_create_group.*

class CreateGroupFragment :
    BaseFeedFragment(),
    FeedGroupAddParticipantsAdapter.ParticipantInteraction {

    private lateinit var recyclerAdapter: FeedGroupAddParticipantsAdapter
    private lateinit var localFriendList: MutableList<GroupParticipants>

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView_back.setOnClickListener {
            findNavController().navigateUp()
        }
        floatingActionButton_next.setOnClickListener{
            createGroup()
        }
        initRecyclerView()
        subscriberObservers()
    }

    private fun subscriberObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { datastate ->
            if (datastate != null) {
                datastate.data?.let {
                    it.data?.let {
                        it.getContentIfNotHandled()?.let {
                            it.feedLoadFromCache?.let {
                                viewModel.setFeedLoadFromCache(it)
                            }
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                it.feedLoadFromCache?.let {
                    setGroupParticipants(it)
                }
            }
        })
    }

    private fun setGroupParticipants(feedLoadFromCache: FeedLoadFromCache) {
        localFriendList = ArrayList()
        for (friend in feedLoadFromCache.friendsAndFriendStories) {
            val groupParticipants = GroupParticipants(
                f_name = friend.feedFriendStore.f_name,
                l_name = friend.feedFriendStore.l_name,
                id = friend.feedFriendStore.user_id,
                profile_picture = friend.feedFriendStore.profile_picture
            )
            localFriendList.add(groupParticipants)
        }
        recyclerAdapter.submitList(localFriendList)
    }

    private fun initRecyclerView() {
        recyclerView_participants.apply {
            layoutManager = LinearLayoutManager(this@CreateGroupFragment.context)
            recyclerAdapter = FeedGroupAddParticipantsAdapter(
                requestManger = requestManager,
                selectedParticipantsInteraction = this@CreateGroupFragment,
                context = requireActivity()
            )
            adapter = recyclerAdapter
        }
    }

    private fun createGroup(){
        viewModel.setStateEvent(
            FeedStateEvent.CreateGroup(
                editText_groupName.text.toString(),
            localFriendList)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.setStateEvent(FeedStateEvent.GetFeedEvent)
    }

    data class GroupParticipants(
        val f_name: String = "",
        val l_name: String = "",
        val id: Int = -1,
        val profile_picture: String = "",
        var isSelected: Boolean = false
    )

    override fun onParticipantSelected(position: Int) {
        Log.d(TAG, "onParticipantSelected: position: ${position}")
        localFriendList[position].isSelected = !localFriendList[position].isSelected
        /*
        * Each time AsyncListDiffer received my list; it was the same object as previously -
        * present in memory. Hence, the differ decided nothing changed and did not submit
        *  the updated list. My list contained one object inside, and for each submission
        * attempt I was changing one field. The object and the list of course remained t
        * he same.*/
        recyclerAdapter.submitList(localFriendList.map {
            it.copy()
        })
    }
}