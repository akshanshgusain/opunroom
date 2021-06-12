package com.factor8.opUndoor.UI.Main.Feed

import android.net.Network
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.factor8.opUndoor.Models.Relationships.FeedNetworkStoreAndFeedCompanyStoryPicture
import com.factor8.opUndoor.R
import com.factor8.opUndoor.Util.Constants
import kotlinx.android.synthetic.main.single_vertical_row.view.*

class FeedNetworkListAdapter(
    private val feedNetworkStoreAndFeedCompanyStoryPicture: List<FeedNetworkStoreAndFeedCompanyStoryPicture>,
    private val requestManager: RequestManager,
    private val networkInteraction: FeedFragment
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG: String = "debug"

    val DIFF_CALLBACK = object: DiffUtil.ItemCallback<FeedNetworkStoreAndFeedCompanyStoryPicture>(){
        override fun areItemsTheSame(
            oldItem: FeedNetworkStoreAndFeedCompanyStoryPicture,
            newItem: FeedNetworkStoreAndFeedCompanyStoryPicture
        ): Boolean {
            return oldItem.feedNetworkStore.feed_network_pk ==
                    newItem.feedNetworkStore.feed_network_pk
        }

        override fun areContentsTheSame(
            oldItem: FeedNetworkStoreAndFeedCompanyStoryPicture,
            newItem: FeedNetworkStoreAndFeedCompanyStoryPicture
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        NetworkRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    internal inner class NetworkRecyclerChangeCallback(
        private val adapter: FeedNetworkListAdapter
    ): ListUpdateCallback{
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_vertical_row, parent, false)
        return FeedNetworkViewHolder(
            view,
            networkInteraction = networkInteraction,
            requestManager = requestManager
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FeedNetworkViewHolder).bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Prepare the images that will be displayed in the RecyclerView
    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<FeedNetworkStoreAndFeedCompanyStoryPicture>
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

    fun submitList(networkList: List<FeedNetworkStoreAndFeedCompanyStoryPicture>){
        differ.submitList(networkList)
    }

    class FeedNetworkViewHolder(
        itemView: View,
        private val requestManager: RequestManager,
        private val networkInteraction: FeedFragment
    ): RecyclerView.ViewHolder(itemView){

        fun bind(item: FeedNetworkStoreAndFeedCompanyStoryPicture) = with(itemView){
            itemView.setOnClickListener {
                networkInteraction.onNetworkSelected(
                    adapterPosition,
                    item
                )
            }
            requestManager
                .load(Constants.COMPANY_IMAGES + item.feedNetworkStore.profile_picture)
                .into(itemView.imageView_company_image)
            requestManager
                .load(Constants.COMPANY_IMAGES + item.feedNetworkStore.cover_picture)
                .into(itemView.imaegView_banner_image)

            itemView.textView_company_title.text = item.feedNetworkStore.name
        }
    }
    interface NetworkInteraction{
        fun onNetworkSelected(
            position: Int,
            item: FeedNetworkStoreAndFeedCompanyStoryPicture
        )
    }
}