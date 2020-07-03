package org.akshanshgusain.killmonger2test;

import android.content.Context;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.akshanshgusain.killmonger2test.Network.Feed;
import org.akshanshgusain.killmonger2test.Network.Groups;

import java.util.ArrayList;

public class AdapterHorizontal2 extends RecyclerView.Adapter<AdapterHorizontal2.ViewHolderH> {
    private ArrayList<Feed.GroupsBean> mDataObjects;
    private Context mContext;
    private Horizontal2ClickListener horizontal2ClickListener;

    public AdapterHorizontal2(ArrayList<Feed.GroupsBean> mDataObjects, Context mContext) {
        this.mDataObjects = mDataObjects;
        this.mContext = mContext;
        this.horizontal2ClickListener = (Horizontal2ClickListener) mContext;
    }

    public void dataChanged(ArrayList<Feed.GroupsBean> groups){
        this.mDataObjects=groups;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 890;
        }
        return super.getItemViewType(position);
    }


    @NonNull
    @Override
    public ViewHolderH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 890) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_horizontal_row2_add, parent, false);
            ViewHolderH vhh = new ViewHolderH(view, horizontal2ClickListener);
            return vhh;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_horizontal_row2, parent, false);
        ViewHolderH vhh = new ViewHolderH(view, horizontal2ClickListener);
        return vhh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderH holder, int position) {
        if (position != 0) {
            Glide.with(mContext).load(mDataObjects.get(position).getAdminpicture()).into(holder.dp);
            holder.title.setText(mDataObjects.get(position).getGrouptitle());
            holder.adminName.setText(mDataObjects.get(position).getAdminname());
            switch (Utils.getRandomNumber()) {
                case 0:
                    holder.backGround.setBackgroundResource(R.color.colorGroup1);
                    break;
                case 1:
                    holder.backGround.setBackgroundResource(R.color.colorGroup2);
                    break;
                case 2:
                    holder.backGround.setBackgroundResource(R.color.colorGroup3);
                    break;
                case 3:
                    holder.backGround.setBackgroundResource(R.color.colorGroup4);
                    break;
                case 4:
                    holder.backGround.setBackgroundResource(R.color.colorGroup5);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataObjects.size();
    }

    public class ViewHolderH extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView dp;
        TextView title, adminName;
        ConstraintLayout backGround;
        private Horizontal2ClickListener horizontal2ClickListener;

        public ViewHolderH(View itemView, Horizontal2ClickListener horizontal2ClickListener) {
            super(itemView);
            dp = (ImageView) itemView.findViewById(R.id.imageView_dp);
            title = (TextView) itemView.findViewById(R.id.textView_group_title);
            adminName = itemView.findViewById(R.id.textView_group_admin);
            backGround = itemView.findViewById(R.id.constraint_background);
            this.horizontal2ClickListener = horizontal2ClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            horizontal2ClickListener.onHorizontal2ClickListener(getAdapterPosition());
        }


        @Override
        public boolean onLongClick(View view) {
            horizontal2ClickListener.onHorizontal2LongClickListener(getAdapterPosition());
            return true;
        }
    }

    public interface Horizontal2ClickListener {
        void onHorizontal2ClickListener(int position);
        void onHorizontal2LongClickListener(int position);
    }


}
