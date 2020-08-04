package com.factor8.opUndoor.SwipableViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.factor8.opUndoor.Network.Company;
import com.factor8.opUndoor.Network.Friends;
import com.factor8.opUndoor.Network.NewsChannel;
import com.factor8.opUndoor.Network.RestCalls;
import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.SingleHeadingList;
import com.bumptech.glide.Glide;

import com.factor8.opUndoor.MainAdapter;
import com.factor8.opUndoor.Network.Feed;
import com.factor8.opUndoor.Network.Groups;
import com.factor8.opUndoor.Profile.ProfileMainActivity;


import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DashBoardFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Object> mObjects = new ArrayList<>();
    public static List<Feed.GroupsBean> groupsGlobal = new ArrayList<>();
    public static List<Feed.FriendsBean> friendsGlobal = new ArrayList<>();
    public static List<Feed.CompanyBean> companiesGlobal = new ArrayList<>();
    public static List<NewsChannel> channelsGlobal = new ArrayList<>();

    SharedPreferences pref;
    TextView fullName;
    ImageView imageView;
    static MainAdapter mainAdapter;
    RestCalls restCalls;

    static boolean isVerticalDataReady = false;
    static boolean isHorizontal1DataReady = false;
    static boolean isHorizontal2DataReady = false;
    static ProgressBar progressBar;

    private ImageView mCameraButton, mSearchButton;
    private ButtonClickListener buttonClickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        restCalls = new RestCalls(getActivity());
        buttonClickListener = (ButtonClickListener)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        pref = getActivity().getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        mRecyclerView = view.findViewById(R.id.main_recyclerView);
        mainAdapter = new MainAdapter(getObject(), getActivity());
        mRecyclerView.setAdapter(mainAdapter);
        mRecyclerView.setNestedScrollingEnabled(true);

        mCameraButton = view.findViewById(R.id.imageView_cancel_button);
        mSearchButton = view.findViewById(R.id.imageView_search_button);
        progressBar = view.findViewById(R.id.progressBar_load);
        progressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        imageView = view.findViewById(R.id.imageView_dp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileMainActivity.class));
            }
        });
        fullName = view.findViewById(R.id.textView_name);
        fullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileMainActivity.class));
            }
        });
        fullName.setText("Hi, " + pref.getString(ProjectConstants.PREF_KEY_F_NAME, ""));
        Glide.with(getActivity()).load(pref.getString(ProjectConstants.PREF_KEY_PICTURE, "")).transition(withCrossFade()).into(imageView);

        //Call Feed Rest API
        restCalls.getFeed(pref.getString(ProjectConstants.PREF_KEY_ID, ""));
        restCalls.getNewsStory(0);
        restCalls.getNewsStory(1);
        restCalls.getNewsStory(2);

        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClickListener.buttonClickListener(2);
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Dashboard", "onResume: ");
        if(pref.getBoolean(ProjectConstants.PREF_KEY_IS_LOGIN,false)){
            fullName.setText("Hi, " + pref.getString(ProjectConstants.PREF_KEY_F_NAME, ""));
            Glide.with(getActivity()).load(pref.getString(ProjectConstants.PREF_KEY_PICTURE, "")).into(imageView);
            //Call Feed Rest API
            restCalls.getFeed(pref.getString(ProjectConstants.PREF_KEY_ID, ""));
            restCalls.getNewsStory(0);
            restCalls.getNewsStory(1);
            restCalls.getNewsStory(2);
        }

    }

    public void CustomOnResumeCallForDashBoard(){
        Log.d("Dashboard", "CustomOnResumeCallForDashBoard: ");
        fullName.setText("Hi, " + pref.getString(ProjectConstants.PREF_KEY_F_NAME, ""));
        Glide.with(getActivity()).load(pref.getString(ProjectConstants.PREF_KEY_PICTURE, "")).into(imageView);
        //Call Feed Rest API
        restCalls.getFeed(pref.getString(ProjectConstants.PREF_KEY_ID, ""));
        restCalls.getNewsStory(0);
        restCalls.getNewsStory(1);
        restCalls.getNewsStory(2);
    }



    public ArrayList<Object> getObject() {
        mObjects.add(getHeadingData().get(0));
        mObjects.add(new Friends("null", "null", "null", "null", "null", "null", "null", "null", null, null));
        mObjects.add(getHeadingData().get(0));
        mObjects.add(new Groups(null, null, null, null));
        mObjects.add(getHeadingData().get(0));
        mObjects.add(new Company("", "", "", "", "", "", null));
        mObjects.add(getHeadingData().get(0));
        mObjects.add(new NewsChannel());
        return mObjects;
    }

    public static ArrayList<SingleHeadingList> getHeadingData() {
        ArrayList<SingleHeadingList> mTemp = new ArrayList<>();
        mTemp.add(new SingleHeadingList("Colleagues"));
        mTemp.add(new SingleHeadingList("Teams"));
        mTemp.add(new SingleHeadingList("Companies"));
        mTemp.add(new SingleHeadingList("News"));
        return mTemp;
    }

    public static ArrayList<Feed.FriendsBean> getHorizontalData(List<Feed.FriendsBean> friends) {
        friendsGlobal = friends;
        mainAdapter.horizontalDataSetChanged();
        isHorizontal1DataReady = true;
        checkLoad();
        return (ArrayList<Feed.FriendsBean>) friendsGlobal;
    }

    //Groups Data.
    public static ArrayList<Feed.GroupsBean> getHorizontal2Data(List<Feed.GroupsBean> groups) {
        groupsGlobal = groups;
        mainAdapter.horizontal2DataSetChanged();
        isHorizontal2DataReady = true;
        checkLoad();
        return (ArrayList<Feed.GroupsBean>) groupsGlobal;
    }

    public static ArrayList<Feed.CompanyBean> getVerticalData(List<Feed.CompanyBean> companies) {
        companiesGlobal = companies;
        mainAdapter.verticalDataSetChanged();
        isVerticalDataReady = true;
        checkLoad();
        return (ArrayList<Feed.CompanyBean>) companiesGlobal;
    }

    public static ArrayList<NewsChannel> getVertical2Data(List<NewsChannel> channels) {
        channelsGlobal = channels;
        mainAdapter.vertical2DataSetChanged();
        isVerticalDataReady = true;
        checkLoad();
        return (ArrayList<NewsChannel>) channelsGlobal;
    }

    private static void checkLoad() {
        if (isVerticalDataReady && isHorizontal2DataReady && isHorizontal1DataReady) {
            progressBar.setVisibility(View.GONE);
        }
    }




}
