package com.factor8.opUndoor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.factor8.opUndoor.Network.Responses.Company;
import com.factor8.opUndoor.Network.Responses.Feed;
import com.factor8.opUndoor.Network.Responses.Friends;
import com.factor8.opUndoor.Network.Responses.NewsChannel;
import com.factor8.opUndoor.SwipableViews.DashBoardFragment;
import com.factor8.opUndoor.Network.Responses.Groups;


import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mObjectList;
    private Context mContext;
    //Constants Integers Value
    private static final int HORIZONTAL = 1;
    private static final int VERTICAL = 2;
    private static final int HORIZONTAL2 = 3;
    public static final int  HEADING = 4;
    private static final int VERTICAL2 = 5;

    AdapterHorizontal2 adapter2;
    AdapterHorizontal adapter;
    AdapterVertical adapter1;
    AdapterVertical2 adapter3;
     EnableDisableScrollInViewPager enableDisableScrollInViewPager ;
    //Constructor
    public MainAdapter(ArrayList<Object> mObjectList, Context mContext) {
        this.mObjectList = mObjectList;
        this.mContext = mContext;
        this.enableDisableScrollInViewPager  =  (EnableDisableScrollInViewPager) mContext;
        Log.d("MainAdapter", "MainAdapter: Size: "+mObjectList.size() );
    }

    //ItemView Type
    @Override
    public int getItemViewType(int position) {
        if (mObjectList.get(position) instanceof Company)
            return VERTICAL;
        if (mObjectList.get(position) instanceof Friends)
            return HORIZONTAL;
        if (mObjectList.get(position) instanceof Groups)
            return HORIZONTAL2;
        if (mObjectList.get(position) instanceof SingleHeadingList){
            return HEADING;
        }
        if (mObjectList.get(position) instanceof NewsChannel){
            return VERTICAL2;
        }

        else
            return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder vh;

        switch (viewType) {
            case HORIZONTAL:
                view = mLayoutInflater.inflate(R.layout.horizontal, parent, false);
                vh = new ViewHolderHorizontal(view,enableDisableScrollInViewPager);
                return vh;
            case VERTICAL:
                view = mLayoutInflater.inflate(R.layout.vertical, parent, false);
                vh = new ViewHolderVertical(view);
                return vh;
            case HORIZONTAL2:
                view = mLayoutInflater.inflate(R.layout.horizontal2, parent, false);
                vh = new ViewHolderHorizontal2(view, enableDisableScrollInViewPager);
                return vh;
            case HEADING:
                view = mLayoutInflater.inflate(R.layout.single_heading_row, parent, false);
                vh = new ViewHolderHeading(view);
                return vh;
            case VERTICAL2:
                view = mLayoutInflater.inflate(R.layout.vertical, parent, false);
                vh = new ViewHolderVertical2(view);
                return vh;
            default:
                view = mLayoutInflater.inflate(R.layout.single_vertical_row, parent, false);
                vh = new ViewHolderVertical(view);
                return vh;
        }
    }//end of OnCreate View Holder

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == HORIZONTAL)
            horizontalView((ViewHolderHorizontal) holder);
        else if (holder.getItemViewType() == VERTICAL)
            verticalView((ViewHolderVertical) holder);
        else if (holder.getItemViewType() == HORIZONTAL2)
            horizontal2View((ViewHolderHorizontal2) holder);
        else if (holder.getItemViewType() == HEADING)
             headingView((ViewHolderHeading) holder, position);
        else if (holder.getItemViewType() == VERTICAL2)
            verticalView2((ViewHolderVertical2) holder);

    }


    private void verticalView(ViewHolderVertical holder) {
         adapter1 = new AdapterVertical(DashBoardFragment.companiesGlobal, mContext);
        //holder.mRecyclerViewV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.mRecyclerViewV.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.mRecyclerViewV.setAdapter(adapter1);
    }

    private void verticalView2(ViewHolderVertical2 holder) {
        adapter3 = new AdapterVertical2(DashBoardFragment.channelsGlobal, mContext);
        //holder.mRecyclerViewV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.mRecyclerViewV.setLayoutManager(new GridLayoutManager(mContext, 2));

        holder.mRecyclerViewV.setAdapter(adapter3);
    }

    private void horizontalView(ViewHolderHorizontal holder) {
         adapter = new AdapterHorizontal((ArrayList<Feed.FriendsBean>) DashBoardFragment.friendsGlobal, mContext);
        holder.mRecyclerViewH.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.mRecyclerViewH.setAdapter(adapter);
    }

    private void horizontal2View(ViewHolderHorizontal2  holder) {
         adapter2 = new AdapterHorizontal2((ArrayList<Feed.GroupsBean>) DashBoardFragment.groupsGlobal, mContext);
        holder.mRecyclerViewH2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.mRecyclerViewH2.setAdapter(adapter2);
    }
   public  void horizontal2DataSetChanged(){
         if(adapter2!=null){
             adapter2.dataChanged((ArrayList<Feed.GroupsBean>) DashBoardFragment.groupsGlobal);
         }
    }

    public  void horizontalDataSetChanged(){
        if(adapter!=null){
            adapter.dataChanged((ArrayList<Feed.FriendsBean>) DashBoardFragment.friendsGlobal);
        }
    }

    public void verticalDataSetChanged(){
         if(adapter1!=null){
              adapter1.dataChanged((ArrayList<Feed.NetworkBean>) DashBoardFragment.companiesGlobal);
         }
    }
    public void vertical2DataSetChanged(){
        if(adapter3!=null){
            adapter3.dataChanged(DashBoardFragment.channelsGlobal);
        }
    }


    private void headingView(ViewHolderHeading  holder, int position) {
       switch(position){
           case 0:
               holder.heading.setText(DashBoardFragment.getHeadingData().get(0).getHeading());
              // holder.itemView.setBackgroundColor(mContext.getColor(R.color.colorGrey));
                break;
           case 2: holder.heading.setText(DashBoardFragment.getHeadingData().get(1).getHeading()); break;
           case 4: holder.heading.setText(DashBoardFragment.getHeadingData().get(2).getHeading()); break ;
           case 6: holder.heading.setText(DashBoardFragment.getHeadingData().get(3).getHeading()); break ;
       }

    }



    @Override
    public int getItemCount() {
        return mObjectList.size();
    }


    //ViewHolder Classes
    public class ViewHolderVertical extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerViewV;

        public ViewHolderVertical(View itemView) {
            super(itemView);
            mRecyclerViewV = (RecyclerView) itemView.findViewById(R.id.vertical_recyclerView);
        }
    }
    public class ViewHolderVertical2 extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerViewV;

        public ViewHolderVertical2(View itemView) {
            super(itemView);
            mRecyclerViewV = (RecyclerView) itemView.findViewById(R.id.vertical_recyclerView);

        }
    }

    public class ViewHolderHorizontal extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerViewH;
        EnableDisableScrollInViewPager enableDisableScrollInViewPager;
        public ViewHolderHorizontal(View itemView, final EnableDisableScrollInViewPager enableDisableScrollInViewPager) {
            super(itemView);
            mRecyclerViewH = (RecyclerView) itemView.findViewById(R.id.horizontal_recyclerView);
            this.enableDisableScrollInViewPager = enableDisableScrollInViewPager;

            mRecyclerViewH.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                int lastX = 0;
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                    switch (e.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            lastX = (int) e.getX();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            boolean isScrollingRight = e.getX() < lastX;
                            if ((isScrollingRight && ((LinearLayoutManager) mRecyclerViewH.getLayoutManager())
                                    .findLastCompletelyVisibleItemPosition() == mRecyclerViewH.getAdapter().getItemCount() - 1) ||
                                    (!isScrollingRight && ((LinearLayoutManager) mRecyclerViewH.getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0)) {

                                enableDisableScrollInViewPager.enableDisableScrollInViewPager(true);
                            } else {

                                enableDisableScrollInViewPager.enableDisableScrollInViewPager(false);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            lastX = 0;
                            enableDisableScrollInViewPager.enableDisableScrollInViewPager(true);
                            break;
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
        }
    }
    public class ViewHolderHorizontal2 extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerViewH2;
        EnableDisableScrollInViewPager enableDisableScrollInViewPager;
        public ViewHolderHorizontal2(View itemView , final EnableDisableScrollInViewPager enableDisableScrollInViewPager) {
            super(itemView);
            mRecyclerViewH2 = (RecyclerView) itemView.findViewById(R.id.horizontal2_recyclerView);
            this.enableDisableScrollInViewPager = enableDisableScrollInViewPager;

            mRecyclerViewH2.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                int lastX = 0;
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                    switch (e.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            lastX = (int) e.getX();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            boolean isScrollingRight = e.getX() < lastX;
                         if ((isScrollingRight && ((LinearLayoutManager) mRecyclerViewH2.getLayoutManager())
                                 .findLastCompletelyVisibleItemPosition() == mRecyclerViewH2.getAdapter().getItemCount() - 1) ||
                                    (!isScrollingRight && ((LinearLayoutManager) mRecyclerViewH2.getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0)) {

                                enableDisableScrollInViewPager.enableDisableScrollInViewPager(true);
                            } else {

                                enableDisableScrollInViewPager.enableDisableScrollInViewPager(false);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            lastX = 0;
                            enableDisableScrollInViewPager.enableDisableScrollInViewPager(true);
                            break;
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
        }
    }
    public class ViewHolderHeading extends RecyclerView.ViewHolder {
       TextView heading;

        public ViewHolderHeading(View itemView) {
            super(itemView);
           heading =  itemView.findViewById(R.id.textView_heading);
        }
    }

    public interface EnableDisableScrollInViewPager{
        public void enableDisableScrollInViewPager(boolean enable);
    }

}
