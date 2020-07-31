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
import java.util.List;

public class AdapterVertical extends RecyclerView.Adapter<AdapterVertical.ViewHolderV>{
   private ArrayList<Feed.CompanyBean> mDataObjects;
   private Context mContext;
   private VerticalClickListener verticalClickListener;

    public AdapterVertical(List<Feed.CompanyBean> mDataObjects, Context mContext) {
        this.mDataObjects =(ArrayList<Feed.CompanyBean>) mDataObjects;
        this.mContext = mContext;
        this.verticalClickListener = (VerticalClickListener)mContext;
    }
    public void dataChanged(ArrayList<Feed.CompanyBean> groups){
        this.mDataObjects=groups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_vertical_row,parent,false);
        ViewHolderV vhv=new ViewHolderV(view, verticalClickListener);
        return vhv;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderV holder, int position) {
        Glide.with(mContext).load(mDataObjects.get(position).getProfile_picture()).into(holder.banner);
        Glide.with(mContext).load(mDataObjects.get(position).getDisplay_picture()).into(holder.dp);
        holder.name.setText(mDataObjects.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataObjects.size();
    }

    //View HOlder Class
    public class ViewHolderV extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView banner, dp;
        private TextView name;
        private VerticalClickListener verticalClickListener;

        public ViewHolderV(View itemView, VerticalClickListener verticalClickListener) {
            super(itemView);
            banner=itemView.findViewById(R.id.imaegView_banner_image);
            dp=itemView.findViewById(R.id.imaegView_company_image);
            name = itemView.findViewById(R.id.textView_comapnt_title);
            this.verticalClickListener = verticalClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
               verticalClickListener.verticalClickListener(getAdapterPosition());
        }
    }
    public interface VerticalClickListener{
          public void verticalClickListener(int position);
    }
}
