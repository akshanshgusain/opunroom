package com.factor8.opUndoor.UI.Main.Feed

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.factor8.opUndoor.Models.Relationships.FeedFriendStoreToFeedStoryPicture
import com.factor8.opUndoor.R
import com.factor8.opUndoor.Util.Constants
import com.factor8.opUndoor.Util.Constants.Companion.VIEW_TYPE_ADD_STORY
import kotlinx.android.synthetic.main.single_horizontal_row.view.*
import java.lang.StringBuilder


class FeedFriendListAdapter(
    private val friendStoreToFeedStoryPicture: List<FeedFriendStoreToFeedStoryPicture>,
    private val requestManager: RequestManager,
    private val friendInteraction: FeedFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG: String = "debug"
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeedFriendStoreToFeedStoryPicture>() {

        override fun areItemsTheSame(
            oldItem: FeedFriendStoreToFeedStoryPicture,
            newItem: FeedFriendStoreToFeedStoryPicture
        ): Boolean {
            return oldItem.feedFriendStore.feed_friend_store_pk ==
                    newItem.feedFriendStore.feed_friend_store_pk
        }

        override fun areContentsTheSame(
            oldItem: FeedFriendStoreToFeedStoryPicture,
            newItem: FeedFriendStoreToFeedStoryPicture
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        FriendRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    internal inner class FriendRecyclerChangeCallback(
        private val adapter: FeedFriendListAdapter
    ) : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIEW_TYPE_ADD_STORY
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {

            VIEW_TYPE_ADD_STORY -> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.single_horizontal_row_add, parent, false)
                return FeedFriendViewHolder(
                    view,
                    friendInteraction = friendInteraction,
                    requestManager = requestManager
                )
            }
            else -> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.single_horizontal_row, parent, false)
                return FeedFriendViewHolder(
                    view,
                    friendInteraction = friendInteraction,
                    requestManager = requestManager
                )
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedFriendViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Prepare the images that will be displayed in the RecyclerView
    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<FeedFriendStoreToFeedStoryPicture>
    ) {
//        for (friend in list) {
//            requestManager
//                .load(Constants.PROFILE_IMAGES + friend.feedFriendStore.profile_picture)
//                .preload()
//            for (story in friend.storyPictures) {
//                requestManager
//                    .load( story.story_picture)
//                    .preload()
//
//            }
//        }
    }

    fun submitList(friendsList: List<FeedFriendStoreToFeedStoryPicture>) {
        val tempList: MutableList<FeedFriendStoreToFeedStoryPicture> = ArrayList()
        tempList.add(friendsList[0])
        tempList.addAll(friendsList)
        differ.submitList(tempList)
    }

    class FeedFriendViewHolder(
        itemView: View,
        val requestManager: RequestManager,
        private val friendInteraction: FeedFragment
    ) : RecyclerView.ViewHolder(itemView) {
        val TAG: String = "FeedFriendViewHolder"
        fun bind(item: FeedFriendStoreToFeedStoryPicture) = with(itemView) {

            itemView.setOnClickListener {
                friendInteraction.onFriendSelected(adapterPosition, item)
            }

            if (adapterPosition != 0) {
                requestManager
                    .load(Constants.PROFILE_IMAGES + item.feedFriendStore.profile_picture)
                    .into(itemView.imageView_dp)

                Log.e(
                    "debug",
                    "bind: ${Constants.PROFILE_IMAGES + item.feedFriendStore.profile_picture}"
                )
                itemView.textView_name.text =
                    StringBuilder("${item.feedFriendStore.f_name} ${item.feedFriendStore.l_name}")
                itemView.textView_profession.text = item.feedFriendStore.profession

                if (item.storyPictures.isNotEmpty()) {
                    itemView.imageView_status.visibility = View.VISIBLE
                } else {
                    itemView.imageView_status.visibility = View.GONE
                }
            }


        }
    }

    interface FriendInteraction {
        fun onFriendSelected(
            position: Int,
            item: FeedFriendStoreToFeedStoryPicture
        )
    }


}
