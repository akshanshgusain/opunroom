package com.factor8.opUndoor;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.factor8.opUndoor.Network.Feed;
import com.bumptech.glide.Glide;



import java.util.ArrayList;

public class AdapterHorizontal extends RecyclerView.Adapter<AdapterHorizontal.ViewHolderH> {
    private ArrayList<Feed.FriendsBean> mDataObjects = new ArrayList<>();
    private Context mContext;
    private Horizontal1ClickListener horizontal1ClickListener;

    public AdapterHorizontal(ArrayList<Feed.FriendsBean> mDataObjects, Context mContext) {
        this.mDataObjects = mDataObjects;
        this.mContext = mContext;
        this.horizontal1ClickListener = (Horizontal1ClickListener) mContext;
    }

    public void dataChanged(ArrayList<Feed.FriendsBean> friends){
        this.mDataObjects=friends;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 678) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_horizontal_row_add, parent, false);
            ViewHolderH vhh = new ViewHolderH(view, horizontal1ClickListener);
            return vhh;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_horizontal_row, parent, false);
        ViewHolderH vhh = new ViewHolderH(view, horizontal1ClickListener);
        return vhh;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 678;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderH holder, int position) {
        if(position!=0){
            Feed.FriendsBean friend = mDataObjects.get(position);
            Glide.with(mContext).load(friend.getPicture()).into(holder.dp);
            String name = friend.getF_name() +" "+ friend.getL_name();
            holder.profession.setText(friend.getProfession());
            holder.name.setText(name);

            if(friend.getStorypicture().size()>0){
                holder.status.setVisibility(View.VISIBLE);
            }else{
                holder.status.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataObjects.size();
    }

    //View Holder Class
    public class ViewHolderH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView dp, status;
        TextView name,profession;
        Horizontal1ClickListener clickListener;

        public ViewHolderH(View itemView, Horizontal1ClickListener horizontal1ClickListener) {
            super(itemView);
            dp = (ImageView) itemView.findViewById(R.id.imageView_dp);
            status = (ImageView) itemView.findViewById(R.id.imageView_status);
            name = (TextView) itemView.findViewById(R.id.textView_name);
            profession = itemView.findViewById(R.id.textView_profession);
            this.clickListener = horizontal1ClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onHorizontal1ClickListener(getAdapterPosition());
        }
    }

    public interface Horizontal1ClickListener {
        void onHorizontal1ClickListener(int position);
    }
}
