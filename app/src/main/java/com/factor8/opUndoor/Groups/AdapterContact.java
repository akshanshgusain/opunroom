package com.factor8.opUndoor.Groups;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.factor8.opUndoor.Network.Responses.Friends2;
import com.factor8.opUndoor.Network.Responses.GroupMembers;
import com.factor8.opUndoor.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolder> {
    private List<SingleFriends> mDataList ;
    private GroupMembers mDataListGM;
    private ArrayList<Friends2.FriendsBean> mDataListFriends;
    private Context mContext;
    private OnItemViewClickListener onItemViewClickListener;

    private int type= 0;  // 0 - createGroup  1 - View All Participants  2 -Add Participants 3- Remove Participants

    private static final String TAG = "AdapterContact";

    public AdapterContact(List<SingleFriends> mDataList, Context mContext, int type) {
        this.mDataList = mDataList;
        this.mContext = mContext;
        this.type = type;
        this.onItemViewClickListener = (OnItemViewClickListener)mContext;
    }

    public AdapterContact(GroupMembers mDataListGM, Context mContext, int type) {
        this.mDataListGM = mDataListGM;
        this.mContext = mContext;
        this.type = type;
        this.onItemViewClickListener = (OnItemViewClickListener)mContext;
        Log.d(TAG, "AdapterContact: Constructor call at type = " +type);
    }
    public AdapterContact(ArrayList<Friends2.FriendsBean> mDataListFriends, Context mContext, int type) {
        this.mDataListFriends = mDataListFriends;
        this.mContext = mContext;
        this.type = type;
        this.onItemViewClickListener = (OnItemViewClickListener)mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contacts, parent,false);
        return new ViewHolder(view, onItemViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(type==0){
            SingleFriends contact = mDataList.get(position);
            if(contact!=null){
                holder.username.setText(contact.getF_name()+" "+ contact.getL_name());
                Glide.with(mContext).load(contact.getPicture()).into(holder.dp);
                holder.position = position;
                if(contact.isSelected()){
                    holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
                    holder.username.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                }else{
                    holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    holder.username.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                }
            }
        }else if(type==1){
            Log.d(TAG, "AdapterContact: Binding items call at type = " +type);
            if(position < mDataListGM.getGroups().get(0).getMember().size()){
                GroupMembers.GroupsBean.MemberBean member = mDataListGM.getGroups().get(0).getMember().get(position);
                holder.username.setText(member.getF_name()+" "+ member.getL_name());
                Glide.with(mContext).load(member.getPicture()).into(holder.dp);
                holder.position = position;
            }
        }else if(type==2){
            Friends2.FriendsBean friend = mDataListFriends.get(position);
            if(friend!=null){
                holder.username.setText(friend.getF_name()+" "+ friend.getL_name());
                Glide.with(mContext).load(friend.getPicture()).into(holder.dp);
                holder.position = position;
                if(friend.isSelected()){
                    holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
                    holder.username.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                }else{
                    holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    holder.username.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                }
            }

        }else{
            GroupMembers.GroupsBean.MemberBean friend = mDataListGM.getGroups().get(0).getMember().get(position);
            if(friend!=null){
                holder.username.setText(friend.getF_name()+" "+ friend.getL_name());
                Glide.with(mContext).load(friend.getPicture()).into(holder.dp);
                holder.position = position;
                if(friend.isSelected()){
                    holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
                    holder.username.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                }else{
                    holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    holder.username.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                }
        }
        }

    }

    @Override
    public int getItemCount() {
        if(type==0){
            return mDataList.size();
        }else if(type==1 | type==3){
            Log.d(TAG, "AdapterContact: Constructor call at type = " +type+"  Size: "+mDataListGM.getGroups().get(0).getMember().size());
            return mDataListGM.getGroups().get(0).getMember().size();

        }
        else{
            return mDataListFriends.size();
        }

    }

    public void setModel(ArrayList<SingleFriends> models){
        this.mDataList=models;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
          ImageView dp;
          TextView username;
        OnItemViewClickListener onItemViewClickListener;
        int position;
        View view;

        public ViewHolder(@NonNull View itemView, OnItemViewClickListener onItemViewClickListener) {
            super(itemView);
            dp = itemView.findViewById(R.id.imageView_dp);
            username = itemView.findViewById(R.id.textView_username);
             this.onItemViewClickListener = onItemViewClickListener;
             itemView.setOnClickListener(this);
             view = itemView;
        }

        @Override
        public void onClick(View view) {
            if(type==0) {
                SingleFriends model = mDataList.get(getAdapterPosition());
                if (model.isSelected()) {
                    model.setSelected(false);
                } else {
                    model.setSelected(true);
                }
                mDataList.set(getAdapterPosition(), model);
                if (onItemViewClickListener != null) {

                    onItemViewClickListener.clickListener(getAdapterPosition(), model);


                }
                notifyItemChanged(getAdapterPosition());
            }
            else if(type==2){
                Friends2.FriendsBean model = mDataListFriends.get(getAdapterPosition());
                if (model.isSelected()) {
                    model.setSelected(false);
                } else {
                    model.setSelected(true);
                }
                mDataListFriends.set(getAdapterPosition(), model);
                if (onItemViewClickListener != null) {
                    onItemViewClickListener.clickListenerAddUSer(getAdapterPosition(), model);

                }
                notifyItemChanged(getAdapterPosition());
            }
            else if(type==3){
                GroupMembers.GroupsBean.MemberBean model = mDataListGM.getGroups().get(0).getMember().get(getAdapterPosition());
                if (model.isSelected()) {
                    model.setSelected(false);
                } else {
                    model.setSelected(true);
                }
                mDataListGM.getGroups().get(0).getMember().set(getAdapterPosition(), model);
                if (onItemViewClickListener != null) {
                    onItemViewClickListener.clickListenerDeleteUSer(getAdapterPosition(), model);

                }
                notifyItemChanged(getAdapterPosition());
            }


        }
    }

    interface  OnItemViewClickListener{
          void clickListener(int position, SingleFriends contact);
        void clickListenerAddUSer(int position, Friends2.FriendsBean friend);
        void clickListenerDeleteUSer(int position, GroupMembers.GroupsBean.MemberBean friend);

    }
}
