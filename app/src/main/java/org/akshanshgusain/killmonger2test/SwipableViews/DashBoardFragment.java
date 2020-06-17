package org.akshanshgusain.killmonger2test.SwipableViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.bumptech.glide.Glide;

import org.akshanshgusain.killmonger2test.MainAdapter;
import org.akshanshgusain.killmonger2test.Network.Article;
import org.akshanshgusain.killmonger2test.Network.Company;
import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Groups;
import org.akshanshgusain.killmonger2test.Network.NewsChannel;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.Profile.ProfileMainActivity;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.SingleHeadingList;
import org.akshanshgusain.killmonger2test.SingleHorizontal;
import org.akshanshgusain.killmonger2test.SingleHorizontal2;
import org.akshanshgusain.killmonger2test.SingleVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_F_NAME;
import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_ID;
import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_PICTURE;

public class DashBoardFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Object> mObjects = new ArrayList<>();
    public static List<Groups> groupsGlobal = new ArrayList<>();
    public static List<Friends> friendsGlobal = new ArrayList<>();
    public static List<Company> companiesGlobal = new ArrayList<>();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        restCalls = new RestCalls(getActivity());
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
        fullName.setText("Hi, " + pref.getString(PREF_KEY_F_NAME, ""));
        Glide.with(getActivity()).load(pref.getString(PREF_KEY_PICTURE, "")).into(imageView);

        //Call Feed Rest API
        restCalls.getFeed(pref.getString(PREF_KEY_ID, ""));
        restCalls.getNewsStory(0);
        restCalls.getNewsStory(1);
        restCalls.getNewsStory(2);



        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Dashboard", "onResume: ");
        fullName.setText("Hi, " + pref.getString(PREF_KEY_F_NAME, ""));
        Glide.with(getActivity()).load(pref.getString(PREF_KEY_PICTURE, "")).into(imageView);
        //Call Feed Rest API
        restCalls.getFeed(pref.getString(PREF_KEY_ID, ""));
        restCalls.getNewsStory(0);
        restCalls.getNewsStory(1);
        restCalls.getNewsStory(2);
    }

    public void CustomOnResumeCallForDashBoard(){
        Log.d("Dashboard", "CustomOnResumeCallForDashBoard: ");
        fullName.setText("Hi, " + pref.getString(PREF_KEY_F_NAME, ""));
        Glide.with(getActivity()).load(pref.getString(PREF_KEY_PICTURE, "")).into(imageView);
        //Call Feed Rest API
        restCalls.getFeed(pref.getString(PREF_KEY_ID, ""));
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
        mTemp.add(new SingleHeadingList("Friends"));
        mTemp.add(new SingleHeadingList("Your Groups"));
        mTemp.add(new SingleHeadingList("Subscriptions"));
        mTemp.add(new SingleHeadingList("News"));
        return mTemp;
    }

    public static ArrayList<Friends> getHorizontalData(List<Friends> friends) {
        friendsGlobal = friends;
        mainAdapter.horizontalDataSetChanged();
        isHorizontal1DataReady = true;
        checkLoad();
        return (ArrayList<Friends>) friendsGlobal;
    }

    //Groups Data.
    public static ArrayList<Groups> getHorizontal2Data(List<Groups> groups) {
        groupsGlobal = groups;
        mainAdapter.horizontal2DataSetChanged();
        isHorizontal2DataReady = true;
        checkLoad();
        return (ArrayList<Groups>) groupsGlobal;
    }

    public static ArrayList<Company> getVerticalData(List<Company> companies) {
        companiesGlobal = companies;
        mainAdapter.verticalDataSetChanged();
        isVerticalDataReady = true;
        checkLoad();
        return (ArrayList<Company>) companiesGlobal;
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
