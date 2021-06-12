package com.factor8.opUndoor.UI.Main.Feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.factor8.opUndoor.Models.Relationships.FeedCompanyToFeedCompanyStoryPicture
import com.factor8.opUndoor.Models.Relationships.FeedGroupStoreToFeedGroupStoryPicture
import com.factor8.opUndoor.R
import com.factor8.opUndoor.Util.Constants
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.single_vertical_row.view.*

class FeedCompanyListAdapter(
    private val feedCompanyToFeedCompanyStoryPicture: List<FeedCompanyToFeedCompanyStoryPicture>,
    private val requestManager: RequestManager,
    private val companyInteraction: FeedFragment
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG: String = "debug"

    val DIFF_CALLBACK = object: DiffUtil.ItemCallback<FeedCompanyToFeedCompanyStoryPicture>(){
        override fun areItemsTheSame(
            oldItem: FeedCompanyToFeedCompanyStoryPicture,
            newItem: FeedCompanyToFeedCompanyStoryPicture
        ): Boolean {
            return oldItem.feedCompany.feed_company_pk ==
                    newItem.feedCompany.feed_company_pk
        }

        override fun areContentsTheSame(
            oldItem: FeedCompanyToFeedCompanyStoryPicture,
            newItem: FeedCompanyToFeedCompanyStoryPicture
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        GroupRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    internal inner class GroupRecyclerChangeCallback(
        private val adapter: FeedCompanyListAdapter
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_vertical_row,parent, false)
        return FeedCompanyViewHolder(
            view,
            companyInteraction = companyInteraction,
            requestManager = requestManager
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FeedCompanyViewHolder).bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Prepare the images that will be displayed in the RecyclerView
    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<FeedCompanyToFeedCompanyStoryPicture>
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

    fun submitList(companyList: List<FeedCompanyToFeedCompanyStoryPicture>){
        differ.submitList(companyList)
    }

    class FeedCompanyViewHolder(
        itemView: View,
        private val requestManager: RequestManager,
        private val companyInteraction: FeedFragment
    ): RecyclerView.ViewHolder(itemView){
        fun bind(item: FeedCompanyToFeedCompanyStoryPicture) = with(itemView){
            itemView.setOnClickListener {
                companyInteraction.onCompanySelected(
                    adapterPosition,
                    item
                )
            }
            requestManager
                .load(Constants.COMPANY_IMAGES + item.feedCompany.display_picture)
                .into(itemView.imageView_company_image)
            requestManager
                .load(Constants.COMPANY_IMAGES + item.feedCompany.cover_picture)
                .into(itemView.imaegView_banner_image)

            itemView.textView_company_title.text = item.feedCompany.company_name
        }
    }
    interface CompanyInteraction{
        fun onCompanySelected(
            position: Int,
            item: FeedCompanyToFeedCompanyStoryPicture
        )
    }
}