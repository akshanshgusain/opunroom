package com.factor8.opUndoor.SendPicture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.R;

import java.util.List;

import static com.factor8.opUndoor.ProjectConstants.*;

public class AdapterContacts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<SingleContact> groups;
    Context mContext;
    AdapterClickListener adapterClickListener;

    public static final int HEADING_TYPE = 43;
    public static final int GROUP_TYPE = 45;
    public static final int SELF_TYPE = 44;
    public static final int COMPANY_TYPE = 46;

    public AdapterContacts(List<SingleContact> groups, Context mContext) {
        this.groups = groups;
        this.mContext = mContext;
        this.adapterClickListener = (AdapterClickListener) mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (groups != null) {
            return groups.get(position).getType();
        }
        return 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_send_list_title, parent, false);
                return new ViewHolderHeading(view);
            case GROUP_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_send_list_story, parent, false);
                return new ViewHolderContacts(view, adapterClickListener, GROUP_TYPE);
            case SELF_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_send_list_story, parent, false);
                return new ViewHolderContacts(view, adapterClickListener, SELF_TYPE);
            case COMPANY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_send_list_story, parent, false);
                return new ViewHolderContacts(view, adapterClickListener, COMPANY_TYPE);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SingleContact contact = groups.get(position);
        switch (contact.getType()) {
            case HEADING_TYPE:
                ((ViewHolderHeading) holder).heading.setText(contact.username);
                break;
            case GROUP_TYPE:

                //Glide.with(mContext).load(mContext.getDrawable(R.drawable.ic_group)).into(((ViewHolderContacts) holder).dp);
                if(contact!=null){
                    ((ViewHolderContacts) holder).name.setText(contact.username);
                    if(contact.isSelected()){
                        (  (ViewHolderContacts) holder).view.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
                        (  (ViewHolderContacts) holder).name.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                    }else{
                        (  (ViewHolderContacts) holder).view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                        (  (ViewHolderContacts) holder).name.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    }
                }
                break;
            case COMPANY_TYPE:

                Glide.with(mContext).load(COMPANY_IMAGES+contact.getImage()).into(((ViewHolderContacts) holder).dp);
                if(contact!=null){
                    ((ViewHolderContacts) holder).name.setText(contact.username);
                    if(contact.isSelected()){
                        (  (ViewHolderContacts) holder).view.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
                        (  (ViewHolderContacts) holder).name.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                    }else{
                        (  (ViewHolderContacts) holder).view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                        (  (ViewHolderContacts) holder).name.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    }
                }
                break;
            default:
                ((ViewHolderContacts) holder).name.setText(contact.username);
                Glide.with(mContext).load(PROFILE_IMAGES+contact.getImage()).into(((ViewHolderContacts) holder).dp);
                if(contact!=null){
                    ((ViewHolderContacts) holder).name.setText(contact.username);
                    if(contact.isSelected()){
                        (  (ViewHolderContacts) holder).view.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
                        (  (ViewHolderContacts) holder).name.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                    }else{
                        (  (ViewHolderContacts) holder).view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                        (  (ViewHolderContacts) holder).name.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    }
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolderContacts extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView dp;
        AdapterClickListener adapterClickListener;
        int id;
        View view;

        ViewHolderContacts(@NonNull View itemView, AdapterClickListener adapterClickListener, int id) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_heading);
            dp = itemView.findViewById(R.id.imageView_dp);
            this.adapterClickListener = adapterClickListener;
            this.id = id;
            view = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SingleContact model = groups.get(getAdapterPosition());
            if (model.isSelected()) {
                model.setSelected(false);
            } else {
                model.setSelected(true);
            }
            groups.set(getAdapterPosition(), model);
            if (adapterClickListener != null) {
                adapterClickListener.adapterClickListener(getAdapterPosition(), groups.get(getAdapterPosition()).getUserId(), model);
            }
            notifyItemChanged(getAdapterPosition());
        }
    }

    public class ViewHolderHeading extends RecyclerView.ViewHolder {
        TextView heading;

        public ViewHolderHeading(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.textView_heading);
        }
    }

    public interface AdapterClickListener {
        public void adapterClickListener(int position, String type, SingleContact model);
    }
}
