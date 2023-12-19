package com.factor8.opUndoor.UI.Main.Feed.ListAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.factor8.opUndoor.Models.Relationships.FeedGroupStoreToFeedGroupStoryPicture
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Main.Feed.FeedFragment
import com.factor8.opUndoor.Util.Constants
import com.factor8.opUndoor.Util.Constants.Companion.VIEW_TYPE_ADD_GROUP
import kotlinx.android.synthetic.main.single_horizontal_row2.view.*

class FeedGroupListAdapter(
    private val groupStoreToFeedGroupStoryPicture: List<FeedGroupStoreToFeedGroupStoryPicture>,
    private val requestManager: RequestManager,
    private val groupInteraction: FeedFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG: String = "debug"
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FeedGroupStoreToFeedGroupStoryPicture>() {
        override fun areItemsTheSame(
            oldItem: FeedGroupStoreToFeedGroupStoryPicture,
            newItem: FeedGroupStoreToFeedGroupStoryPicture
        ): Boolean {
            return oldItem.feedGroupStore.feed_group_store_pk ==
                    newItem.feedGroupStore.feed_group_store_pk
        }

        override fun areContentsTheSame(
            oldItem: FeedGroupStoreToFeedGroupStoryPicture,
            newItem: FeedGroupStoreToFeedGroupStoryPicture
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        GroupRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )


    internal inner class GroupRecyclerChangeCallback(
        private val adapter: FeedGroupListAdapter
    ) : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if(position ==0){
            return VIEW_TYPE_ADD_GROUP
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            VIEW_TYPE_ADD_GROUP ->{
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.single_horizontal_row2_add, parent,false)
                return FeedGroupViewHolder(
                    view,
                    groupInteraction = groupInteraction,
                    requestManager = requestManager
                )
            }else ->{
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.single_horizontal_row2, parent, false)
                return FeedGroupViewHolder(
                    view,
                    groupInteraction = groupInteraction,
                    requestManager = requestManager
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FeedGroupViewHolder ->{
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
        list: List<FeedGroupStoreToFeedGroupStoryPicture>
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

    fun submitList(groupList: List<FeedGroupStoreToFeedGroupStoryPicture>){
        val tempList: MutableList<FeedGroupStoreToFeedGroupStoryPicture> = ArrayList()
        tempList.add(groupList[0])
        tempList.addAll(groupList)
        differ.submitList(tempList)
    }

    class FeedGroupViewHolder(
        itemView: View,
        val requestManager: RequestManager,
        val groupInteraction: FeedFragment
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FeedGroupStoreToFeedGroupStoryPicture) = with(itemView) {

            itemView.setOnClickListener {
                groupInteraction.onGroupSelected(adapterPosition, item)
            }

            if(adapterPosition != 0){
                requestManager
                    .load(Constants.PROFILE_IMAGES + item.feedGroupStore.admin_profile_picture)
                    .into(itemView.imageView_dp)

                itemView.textView_group_title.text = item.feedGroupStore.group_title
                itemView.textView_group_admin.text = item.feedGroupStore.admin_name

                if(item.groupStoryPictures.isNotEmpty()){
                    itemView.imageView_notification.visibility = View.VISIBLE
                }else{
                    itemView.imageView_notification.visibility = View.GONE
                }

                when (Utils.getRandomNumber()) {

                    0 -> itemView.constraint_background.setBackgroundResource(R.color.colorGroup1)
                    1 -> itemView.constraint_background.setBackgroundResource(R.color.colorGroup2)
                    2 -> itemView.constraint_background.setBackgroundResource(R.color.colorGroup3)
                    3 -> itemView.constraint_background.setBackgroundResource(R.color.colorGroup4)
                    4 -> itemView.constraint_background.setBackgroundResource(R.color.colorGroup5)

                }
            }
        }
    }

    interface GroupInteraction {
        fun onGroupSelected(
            position: Int,
            item: FeedGroupStoreToFeedGroupStoryPicture
        )
    }
}