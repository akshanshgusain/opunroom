package com.factor8.opUndoor.UI.Main.Feed

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.factor8.opUndoor.Models.Relationships.FeedCompanyToFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedFriendStoreToFeedStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedGroupStoreToFeedGroupStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedNetworkStoreAndFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Wrappers.FeedLoadFromCache
import com.factor8.opUndoor.R
import com.factor8.opUndoor.Util.Constants
import kotlinx.android.synthetic.main.horizontal.view.*
import kotlinx.android.synthetic.main.horizontal2.view.*
import kotlinx.android.synthetic.main.single_heading_row.view.*
import kotlinx.android.synthetic.main.vertical.view.*

// dataType : FeedLoadFromCache
class FeedListAdapter(
    private val requestManager: RequestManager,
    private val context: Context,
    private val interaction: FeedFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val HORIZONTAL = 1
    private val VERTICAL = 2
    private val HORIZONTAL2 = 3
    private val HEADING = 4
    private val VERTICAL2 = 5

    var feedLoadFromCache: FeedLoadFromCache? = null

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            1 -> HORIZONTAL
            3 -> HORIZONTAL2
            5 -> VERTICAL
            7 -> VERTICAL2
            else -> HEADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var view: View
        val layoutInflater = LayoutInflater.from(parent.context)
        lateinit var viewHolder: RecyclerView.ViewHolder

        when (viewType) {
            HORIZONTAL -> {
                view = layoutInflater.inflate(R.layout.horizontal, parent, false)
                return FriendRVViewHolder(
                    itemView = view,
                    interaction = interaction,
                    context = context,
                    requestManager = requestManager
                )
            }
            HORIZONTAL2 -> {
                view = layoutInflater.inflate(R.layout.horizontal2, parent, false)
                return GroupRVViewHolder(
                    itemView = view,
                    interaction = interaction,
                    contextGRVV = context,
                    requestManager = requestManager
                )
            }
            VERTICAL -> {
                view = layoutInflater.inflate(R.layout.vertical, parent, false)
                return CompanyRVViewHolder(
                    itemView = view,
                    interaction = interaction,
                    context = context,
                    requestManager = requestManager
                )
            }

            VERTICAL2 -> {
                view = layoutInflater.inflate(R.layout.vertical, parent, false)
                return NetworkRVViewHolder(
                    itemView = view,
                    interaction = interaction,
                    context = context,
                    requestManager = requestManager
                )
            }

            HEADING -> {
                view = layoutInflater.inflate(R.layout.single_heading_row, parent, false)
                return HeadingViewHolder(
                    itemView = view
                )
            }
            else -> {
                view = layoutInflater.inflate(R.layout.horizontal, parent, false)
                return FriendRVViewHolder(
                    itemView = view,
                    interaction = interaction,
                    context = context,
                    requestManager = requestManager
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HORIZONTAL -> {
                (holder as FriendRVViewHolder).bind(feedLoadFromCache!!.friendsAndFriendStories)
            }
            HORIZONTAL2 -> {
                (holder as GroupRVViewHolder).bind(feedLoadFromCache!!.groupsAndGroupStories)
            }
            VERTICAL -> {
                (holder as CompanyRVViewHolder).bind(feedLoadFromCache!!.companyAndCompanyStories)
            }
            VERTICAL2 -> {
                (holder as NetworkRVViewHolder).bind(feedLoadFromCache!!.networkAndNetworkStories)
            }
            HEADING -> {
                (holder as HeadingViewHolder).bind()
            }
        }
    }


    override fun getItemCount(): Int {
        if (feedLoadFromCache != null) {
            return 8;
        } else {
            return 0;
        }
    }

    fun submitList(feedLoadFromCache: FeedLoadFromCache) {
        this.feedLoadFromCache = feedLoadFromCache
        notifyDataSetChanged()
    }

    //View Holder classes ---->>

    // Vertical 1
    class CompanyRVViewHolder(
        itemView: View,
        val interaction: FeedFragment,
        val context: Context,
        val requestManager: RequestManager
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            companyList: List<FeedCompanyToFeedCompanyStoryPicture>
        ) = with(itemView) {
            val feedCompanyListAdapter = FeedCompanyListAdapter(
                feedCompanyToFeedCompanyStoryPicture = companyList,
                requestManager = requestManager,
                companyInteraction = interaction
            )

            feedCompanyListAdapter.apply {
                preloadGlideImages(requestManager, companyList)
                submitList(companyList)
            }

            vertical_recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = feedCompanyListAdapter
            }
        }
    }

    // Vertical 2
    class NetworkRVViewHolder(
        itemView: View,
        val interaction: FeedFragment,
        val context: Context,
        val requestManager: RequestManager
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            networkList: List<FeedNetworkStoreAndFeedCompanyStoryPicture>
        ) = with(itemView) {
            val feedNetworkListAdapter = FeedNetworkListAdapter(
                feedNetworkStoreAndFeedCompanyStoryPicture = networkList,
                requestManager = requestManager,
                networkInteraction = interaction
            )

            feedNetworkListAdapter.apply {
                preloadGlideImages(requestManager, networkList)
                submitList(networkList)
            }
            vertical_recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = feedNetworkListAdapter
            }
        }
    }

    // Horizontal 1
    class FriendRVViewHolder(
        itemView: View,
        val interaction: FeedFragment,
        val context: Context,
        val requestManager: RequestManager
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            friendList: List<FeedFriendStoreToFeedStoryPicture>
        ) = with(itemView) {
            val feedFriendListAdapter = FeedFriendListAdapter(
                friendStoreToFeedStoryPicture = friendList,
                requestManager = requestManager,
                friendInteraction = interaction
            )
            feedFriendListAdapter.apply {
                preloadGlideImages(requestManager, friendList)
                submitList(friendList)
            }
            horizontal_recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = feedFriendListAdapter
            }
        }


    }

    // Horizontal 2
    class GroupRVViewHolder(
        itemView: View,
        val interaction: FeedFragment,
        val contextGRVV: Context,
        val requestManager: RequestManager
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            groupList: List<FeedGroupStoreToFeedGroupStoryPicture>
        ) = with(itemView) {
            val feedGroupListAdapter = FeedGroupListAdapter(
                groupStoreToFeedGroupStoryPicture = groupList,
                requestManager = requestManager,
                groupInteraction = interaction
            )
            feedGroupListAdapter.apply {
                preloadGlideImages(requestManager, groupList)
                submitList(groupList)
            }
            horizontal2_recyclerView.apply {
                layoutManager =
                    LinearLayoutManager(contextGRVV, LinearLayoutManager.HORIZONTAL, false)
                adapter = feedGroupListAdapter
            }
        }
    }

    class HeadingViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {
            itemView.textView_heading.text = when (adapterPosition) {
                0 -> Constants.HEADINGS[0]
                2 -> Constants.HEADINGS[1]
                4 -> Constants.HEADINGS[2]
                6 -> Constants.HEADINGS[3]
                else ->Constants.HEADINGS[0]
            }
        }
    }
}