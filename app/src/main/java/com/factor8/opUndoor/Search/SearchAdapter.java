package com.factor8.opUndoor.Search;

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
import com.factor8.opUndoor.Network.Responses.SearchResult;
import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchResultViewHolder> {
    private Context mContext;
    private List<SearchResult.UserBean> userList;
    private List<SearchResult.CompanyBean> companyList;
    private Interaction interaction;

    public SearchAdapter(Context mContext, SearchResult searchResult) {
        this.mContext = mContext;
        this.userList = searchResult.getUser();
        this.companyList = searchResult.getCompany();
        this.interaction = (Interaction) mContext;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_search_results, parent, false);
        return new SearchResultViewHolder(view, interaction);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {

        if (userList.size() != 0 && companyList.size() == 0) {
            SearchResult.UserBean user = userList.get(position);
            //Only Users are there

            Glide.with(mContext).load(ProjectConstants.PROFILE_IMAGES + user.getCoverpic()).placeholder(R.mipmap.default_cover).into(holder.mBanner);
            Glide.with(mContext).load(ProjectConstants.PROFILE_IMAGES + user.getPicture()).into(holder.mDisplayPicture);
            holder.mFullName.setText(user.getF_name() + " " + user.getL_name());

        } else if (userList.size() == 0 && companyList.size() != 0) {
            SearchResult.CompanyBean company = companyList.get(position);
            //Only Company are there
            Glide.with(mContext).load(ProjectConstants.COMPANY_IMAGES + company.getProfile_picture()).into(holder.mBanner);
            Glide.with(mContext).load(ProjectConstants.COMPANY_IMAGES + company.getDisplay_picture()).into(holder.mDisplayPicture);
            holder.mFullName.setText(company.getNetwork());
        } else if (userList.size() != 0 && companyList.size() != 0) {
            //We have both users and companies

            if (position < userList.size()) {
                SearchResult.UserBean user = userList.get(position);
                Glide.with(mContext).load(ProjectConstants.PROFILE_IMAGES + user.getCoverpic()).placeholder(R.mipmap.default_cover).into(holder.mBanner);
                Glide.with(mContext).load(ProjectConstants.PROFILE_IMAGES + user.getPicture()).into(holder.mDisplayPicture);
                holder.mFullName.setText(user.getF_name() + " " + user.getL_name());


            } else {
                SearchResult.CompanyBean company9 = companyList.get(position - userList.size());
                Glide.with(mContext).load(ProjectConstants.COMPANY_IMAGES + company9.getProfile_picture()).into(holder.mBanner);
                Glide.with(mContext).load(ProjectConstants.COMPANY_IMAGES + company9.getDisplay_picture()).into(holder.mDisplayPicture);
                holder.mFullName.setText(company9.getNetwork());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (userList.size() == 0 && companyList.size() == 0) {
            return 0;
        } else if (userList.size() != 0 && companyList.size() == 0) {

            return userList.size();
        } else if (userList.size() == 0 && companyList.size() != 0) {
            return companyList.size();
        } else {
            return (userList.size() + companyList.size());
        }
    }


    class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mBanner, mDisplayPicture;
        private TextView mFullName;
        private Interaction interaction;

        public SearchResultViewHolder(@NonNull View itemView, Interaction interaction) {
            super(itemView);
            this.interaction = interaction;
            itemView.setOnClickListener(this);
            mBanner = itemView.findViewById(R.id.imageView_banner_image);
            mDisplayPicture = itemView.findViewById(R.id.imageView_dp);
            mFullName = itemView.findViewById(R.id.textView_full_name);
        }

        @Override
        public void onClick(View view) {
            sendInteraction();
            Log.d("clcik", "onClick: ");
        }

        private void sendInteraction() {
            if (userList.size() != 0 && companyList.size() == 0) {
                //Only Users are there
                interaction.userSelected(userList.get(getAdapterPosition()));

            } else if (userList.size() == 0 && companyList.size() != 0) {
                //Only Company are there
                interaction.companySelected(companyList.get(getAdapterPosition()));

            } else if (userList.size() != 0 && companyList.size() != 0) {
                //We have both users and companies
                if (getAdapterPosition() < userList.size()) {
                    interaction.userSelected(userList.get(getAdapterPosition()));

                } else {
                    interaction.companySelected(companyList.get(getAdapterPosition() - userList.size()));

                }
            }
        }
    }

    interface Interaction {
        public void userSelected(SearchResult.UserBean user);

        public void companySelected(SearchResult.CompanyBean company);
    }
}
