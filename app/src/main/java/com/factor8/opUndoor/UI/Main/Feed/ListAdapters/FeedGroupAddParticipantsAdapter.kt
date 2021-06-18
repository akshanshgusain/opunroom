package com.factor8.opUndoor.UI.Main.Feed.ListAdapters

import android.content.Context
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.factor8.opUndoor.R
import com.factor8.opUndoor.UI.Main.Feed.CreateGroupFragment
import com.factor8.opUndoor.UI.Main.Feed.CreateGroupFragment.*
import com.factor8.opUndoor.Util.Constants
import kotlinx.android.synthetic.main.single_contacts.view.*

class FeedGroupAddParticipantsAdapter(
    private val requestManger: RequestManager,
    private val selectedParticipantsInteraction: CreateGroupFragment,
    private val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG: String = "AppDebug"

    val DIFF_CALLBACK = object: DiffUtil.ItemCallback<GroupParticipants>(){
        override fun areItemsTheSame(
            oldItem: GroupParticipants,
            newItem: GroupParticipants
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GroupParticipants,
            newItem: GroupParticipants
        ): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }

    }

    private val differ = AsyncListDiffer(
        AddParticipantsCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    internal inner class AddParticipantsCallback(
        private val adapter: FeedGroupAddParticipantsAdapter
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_contacts, parent,false)
        return ParticipantsViewHolder(
            view,
            requestManager = requestManger,
            selectedParticipantsInteraction = selectedParticipantsInteraction,
            context = context
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ParticipantsViewHolder).bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<GroupParticipants>){
        differ.submitList(list)
    }

    class ParticipantsViewHolder(
        itemView: View,
        private val requestManager: RequestManager,
        private val selectedParticipantsInteraction: CreateGroupFragment,
        private val context: Context
        ): RecyclerView.ViewHolder(itemView){
            fun bind(item : GroupParticipants){

                itemView.setOnClickListener {
                    selectedParticipantsInteraction.onParticipantSelected(adapterPosition)
                }

                itemView.textView_username.text = StringBuilder(item.f_name +" "+ item.l_name)

                requestManager
                    .load(Constants.PROFILE_IMAGES+item.profile_picture)
                    .into(itemView.imageView_dp)
                
                //change color
                if(item.isSelected){
                    itemView.setBackgroundColor(
                        ContextCompat.getColor(context,
                            R.color.colorBlack)
                    )
                    itemView.textView_username.setTextColor(
                        ContextCompat.getColor(context,
                        R.color.colorWhite)
                    )
                }else{
                    itemView.setBackgroundColor(
                        ContextCompat.getColor(context,
                            R.color.colorWhite)
                    )
                    itemView.textView_username.setTextColor(
                        ContextCompat.getColor(context,
                            R.color.colorBlack)
                    )
                }
            }
    }

    interface ParticipantInteraction{
        fun onParticipantSelected(
            position: Int
        )
    }
}