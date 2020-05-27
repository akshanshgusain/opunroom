package org.akshanshgusain.killmonger2test;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Groups;

import java.util.ArrayList;

import static org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment.*;



public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mObjectList;
    private Context mContext;
    //Constants Integers Value
    private static final int HORIZONTAL = 1;
    private static final int VERTICAL = 2;
    private static final int HORIZONTAL2 = 3;
    public static final int  HEADING = 4;

    AdapterHorizontal2 adapter2;
    AdapterHorizontal adapter;

    //Constructor
    public MainAdapter(ArrayList<Object> mObjectList, Context mContext) {
        this.mObjectList = mObjectList;
        this.mContext = mContext;
    }

    //ItemView Type
    @Override
    public int getItemViewType(int position) {
        if (mObjectList.get(position) instanceof SingleVertical)
            return VERTICAL;
        if (mObjectList.get(position) instanceof Friends)
            return HORIZONTAL;
        if (mObjectList.get(position) instanceof Groups)
            return HORIZONTAL2;
        if (mObjectList.get(position) instanceof SingleHeadingList){
            return HEADING;
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
                vh = new ViewHolderHorizontal(view);
                return vh;
            case VERTICAL:
                view = mLayoutInflater.inflate(R.layout.vertical, parent, false);
                vh = new ViewHolderVertical(view);
                return vh;
            case HORIZONTAL2:
                view = mLayoutInflater.inflate(R.layout.horizontal2, parent, false);
                vh = new ViewHolderHorizontal2(view);
                return vh;
            case HEADING:
                view = mLayoutInflater.inflate(R.layout.single_heading_row, parent, false);
                vh = new ViewHolderHeading(view);
                return vh;
            default:
                HORIZONTAL:
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

    }


    private void verticalView(ViewHolderVertical holder) {
        AdapterVertical adapter1 = new AdapterVertical(getVerticalData(), mContext);
        //holder.mRecyclerViewV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.mRecyclerViewV.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.mRecyclerViewV.setAdapter(adapter1);
    }

    private void horizontalView(ViewHolderHorizontal holder) {
         adapter = new AdapterHorizontal((ArrayList<Friends>) friendsGlobal, mContext);
        holder.mRecyclerViewH.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.mRecyclerViewH.setAdapter(adapter);
    }

    private void horizontal2View(ViewHolderHorizontal2  holder) {
         adapter2 = new AdapterHorizontal2((ArrayList<Groups>) groupsGlobal, mContext);
        holder.mRecyclerViewH2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.mRecyclerViewH2.setAdapter(adapter2);
    }
   public  void horizontal2DataSetChanged(){
         if(adapter2!=null){
             adapter2.dataChanged((ArrayList<Groups>)groupsGlobal);
         }
    }

    public  void horizontalDataSetChanged(){
        if(adapter!=null){
            adapter.dataChanged((ArrayList<Friends>)friendsGlobal);
        }
    }


    private void headingView(ViewHolderHeading  holder, int position) {
       switch(position){
           case 0:
               holder.heading.setText(getHeadingData().get(0).getHeading());
               holder.itemView.setBackgroundColor(mContext.getColor(R.color.colorGrey));
                break;
           case 2: holder.heading.setText(getHeadingData().get(1).getHeading()); break;
           case 4: holder.heading.setText(getHeadingData().get(2).getHeading()); break ;
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

    public class ViewHolderHorizontal extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerViewH;

        public ViewHolderHorizontal(View itemView) {
            super(itemView);
            mRecyclerViewH = (RecyclerView) itemView.findViewById(R.id.horizontal_recyclerView);
        }
    }
    public class ViewHolderHorizontal2 extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerViewH2;

        public ViewHolderHorizontal2(View itemView) {
            super(itemView);
            mRecyclerViewH2 = (RecyclerView) itemView.findViewById(R.id.horizontal2_recyclerView);
        }
    }
    public class ViewHolderHeading extends RecyclerView.ViewHolder {
       TextView heading;

        public ViewHolderHeading(View itemView) {
            super(itemView);
           heading =  itemView.findViewById(R.id.textView_heading);
        }
    }

}
