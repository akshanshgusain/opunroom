package org.akshanshgusain.killmonger2test;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterVertical extends RecyclerView.Adapter<AdapterVertical.ViewHolderV>{
   private ArrayList<SingleVertical> mDataObjects;
   private Context mContext;
   private VerticalClickListener verticalClickListener;
    public AdapterVertical(ArrayList<SingleVertical> mDataObjects, Context mContext) {
        this.mDataObjects = mDataObjects;
        this.mContext = mContext;
        this.verticalClickListener = (VerticalClickListener)mContext;
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
        Glide.with(mContext).load(mDataObjects.get(position).getCompanyBanner()).into(holder.banner);
        Glide.with(mContext).load(mDataObjects.get(position).getCoompanyDP()).into(holder.dp);
        holder.name.setText(mDataObjects.get(position).getCompanyName());
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
