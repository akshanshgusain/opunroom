package com.factor8.opUndoor.Search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.factor8.opUndoor.Network.Responses.GetAllFriendRequest;
import com.factor8.opUndoor.Network.Responses.SearchResult;
import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.R;

import java.util.List;

import static com.factor8.opUndoor.ProjectConstants.*;

public class IncomingRequestAdapter extends RecyclerView.Adapter<IncomingRequestAdapter.IncomingRequestViewHolder> {
    private Context mContext;
    private GetAllFriendRequest getAllFriendRequest;
    private Interaction interaction;

    public IncomingRequestAdapter(Context mContext, GetAllFriendRequest getAllFriendRequest) {
        this.mContext = mContext;
        this.getAllFriendRequest = getAllFriendRequest;
        this.interaction = (Interaction)mContext;
    }

    @NonNull
    @Override
    public IncomingRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_search_results, parent,false);
        return new IncomingRequestViewHolder(view, interaction);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomingRequestViewHolder holder, int position) {

        GetAllFriendRequest.RequestBean.UserBean friend =   getAllFriendRequest.getUserlist().get(position).getAllfriendrequestuser().get(0);
        Glide.with(mContext).load(PROFILE_IMAGES+friend.getCoverpic()).into(holder.mBanner);
        Glide.with(mContext).load(PROFILE_IMAGES+friend.getPicture()).into(holder.mDisplayPicture);
        String fullName = friend.getF_name()+" "+friend.getL_name();
        holder.mFullName.setText(fullName);
    }

    @Override
    public int getItemCount() {
       return getAllFriendRequest.getUserlist().size();
    }

    class IncomingRequestViewHolder extends RecyclerView.ViewHolder {
        private ImageView  mBanner, mDisplayPicture;
        private TextView mFullName;
        private Button mAcceptButton, mRejectButton;
        private Interaction interaction;

        public IncomingRequestViewHolder(@NonNull View itemView, final Interaction interaction) {
            super(itemView);
            this.interaction = interaction;

            mBanner = itemView.findViewById(R.id.imageView_banner_image);
            mDisplayPicture = itemView.findViewById(R.id.imageView_dp);
            mFullName = itemView.findViewById(R.id.textView_full_name);

            mAcceptButton = itemView.findViewById(R.id.button_accept);
            mRejectButton = itemView.findViewById(R.id.button_reject);
            mAcceptButton.setVisibility(View.VISIBLE);
            mRejectButton.setVisibility(View.VISIBLE);

            mAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetAllFriendRequest.RequestBean.UserBean friend =
                            getAllFriendRequest.getUserlist().get(getAdapterPosition()).getAllfriendrequestuser().get(0);


                    interaction.requestAccepted(getAdapterPosition(),friend );
                    getAllFriendRequest.getUserlist().remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

            mRejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetAllFriendRequest.RequestBean.UserBean friend =
                            getAllFriendRequest.getUserlist().get(getAdapterPosition()).getAllfriendrequestuser().get(0);


                    interaction.requestRejected(getAdapterPosition(),friend );
                    getAllFriendRequest.getUserlist().remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }

    }

    interface Interaction{
        public void requestAccepted(int position, GetAllFriendRequest.RequestBean.UserBean friend);
        public void requestRejected(int position, GetAllFriendRequest.RequestBean.UserBean friend);
    }
}
