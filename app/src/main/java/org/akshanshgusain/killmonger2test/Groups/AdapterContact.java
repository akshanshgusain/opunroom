package org.akshanshgusain.killmonger2test.Groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.akshanshgusain.killmonger2test.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolder> {
    private List<SingleFriends> mDataList ;
    private Context mContext;
    private OnItemViewClickListener onItemViewClickListener;

    public AdapterContact(List<SingleFriends> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
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

        SingleFriends contact = mDataList.get(position);
          if(contact!=null){
              holder.username.setText(contact.getF_name()+" "+ contact.getL_name());
              Glide.with(mContext).load(contact.getPicture()).into(holder.dp);
              holder.position = position;
                if(contact.isSelected()){
                    holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                }else{
                    holder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                }
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
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
            SingleFriends model = mDataList.get(getAdapterPosition());
             if(model.isSelected()){
                  model.setSelected(false);
             }else{
                 model.setSelected(true);
             }
             mDataList.set(getAdapterPosition(), model);
             if(onItemViewClickListener!=null){
                 onItemViewClickListener.clickListener(getAdapterPosition(), null, model);
             }
             notifyItemChanged(getAdapterPosition());
        }
    }

    interface  OnItemViewClickListener{
          void clickListener(int position, List<Integer> userIds, SingleFriends contact);
    }
}
