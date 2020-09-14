package com.factor8.opUndoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.factor8.opUndoor.Network.Responses.Feed;


import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AdapterVertical2 extends RecyclerView.Adapter<AdapterVertical2.ViewHolderV> {
    private List<Feed.NewsBean.TimesofindiaBean> mDataObjects;
    private List<Feed.NewsBean.EconomictimesBean> mDataObjects2;
    private Context mContext;
    private Vertical2ClickListener vertical2ClickListener;

    private String DP_TOI = "https://dass.io/oppo/newscoverimage/download.jpeg";
    private String DP_ET = "https://dass.io/oppo/newscoverimage/ETDP.jpg";

    private String COVER_TOI = "https://dass.io/oppo/newscoverimage/timesOfIndia_Cover.jpg";
    private String COVER_ET = "https://dass.io/oppo/newscoverimage/economicTimes_Cover.png";

    private String TITLE_TOI = "THE TIMES OF INDIA";
    private String TITLE_ET = "THE ECONOMIC TIMES";

    public AdapterVertical2(Feed.NewsBean mDataObjects, Context mContext) {
        this.mDataObjects = mDataObjects.getTimesofindia();
        this.mDataObjects2 = mDataObjects.getEconomictimes();
        this.mContext = mContext;
        this.vertical2ClickListener = (Vertical2ClickListener) mContext;
    }

    public void dataChanged(Feed.NewsBean groups) {
        this.mDataObjects = groups.getTimesofindia();
        this.mDataObjects2 = groups.getEconomictimes();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolderV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_vertical_row, parent, false);
        ViewHolderV vhv = new ViewHolderV(view, vertical2ClickListener);
        return vhv;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderV holder, int position) {

        switch (position) {
            case 0: {
                Glide.with(mContext).load(COVER_TOI).transition(withCrossFade()).into(holder.banner);
                Glide.with(mContext).load(DP_TOI).transition(withCrossFade()).into(holder.dp);
                holder.dp.setVisibility(View.GONE);
                holder.name.setText(TITLE_TOI);
            }
            ;
            break;
            case 1: {
                Glide.with(mContext).load(COVER_ET).transition(withCrossFade()).into(holder.banner);
                Glide.with(mContext).load(DP_ET).transition(withCrossFade()).into(holder.dp);
                holder.dp.setVisibility(View.GONE);
                holder.name.setText(TITLE_ET);
            }
            ;
            break;
            default: {
                Glide.with(mContext).load(COVER_ET).transition(withCrossFade()).into(holder.banner);
                Glide.with(mContext).load(DP_ET).transition(withCrossFade()).into(holder.dp);
                holder.dp.setVisibility(View.GONE);
                holder.name.setText("INVALID");
            }
            ;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    //View HOlder Class
    public class ViewHolderV extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView banner, dp;
        private TextView name;
        private Vertical2ClickListener vertical2ClickListener;

        public ViewHolderV(View itemView, Vertical2ClickListener vertical2ClickListener) {
            super(itemView);
            banner = itemView.findViewById(R.id.imaegView_banner_image);
            dp = itemView.findViewById(R.id.imaegView_company_image);
            name = itemView.findViewById(R.id.textView_comapnt_title);
            this.vertical2ClickListener = vertical2ClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            vertical2ClickListener.vertical2ClickListener(getAdapterPosition());
        }
    }

    public interface Vertical2ClickListener {
        public void vertical2ClickListener(int position);
    }
}
