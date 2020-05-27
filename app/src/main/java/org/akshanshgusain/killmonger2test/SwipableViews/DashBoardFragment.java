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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.akshanshgusain.killmonger2test.MainAdapter;
import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Groups;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.Profile.ProfileMainActivity;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.SingleHeadingList;
import org.akshanshgusain.killmonger2test.SingleHorizontal;
import org.akshanshgusain.killmonger2test.SingleHorizontal2;
import org.akshanshgusain.killmonger2test.SingleVertical;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_F_NAME;
import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_ID;
import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_PICTURE;

public class DashBoardFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Object> mObjects = new ArrayList<>();
    public static List<Groups> groupsGlobal = new ArrayList<>();
    public static List<Friends> friendsGlobal = new ArrayList<>();
    SharedPreferences pref;
    TextView fullName;
    ImageView imageView;
    static MainAdapter mainAdapter;
    RestCalls restCalls ;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        restCalls = new RestCalls(getActivity()) ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dash_board,container,false);
        pref = getActivity().getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        mRecyclerView = view.findViewById(R.id.main_recyclerView);
         mainAdapter = new MainAdapter(getObject(), getActivity());
        mRecyclerView.setAdapter(mainAdapter);
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
        fullName.setText("Hi, "+ pref.getString(PREF_KEY_F_NAME,""));
        Glide.with(getActivity()).load(pref.getString(PREF_KEY_PICTURE,"")).into(imageView);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        fullName.setText("Hi, "+ pref.getString(PREF_KEY_F_NAME,""));
        Glide.with(getActivity()).load(pref.getString(PREF_KEY_PICTURE,"")).into(imageView);
        //Call Feed Rest API
        restCalls.getFeed(  pref.getString(PREF_KEY_ID,""));
    }

    public ArrayList<Object> getObject() {
        mObjects.add(getHeadingData().get(0));
        mObjects.add(new Friends("null","null","null","null","null","null","null","null",null));
        mObjects.add(getHeadingData().get(0));
        mObjects.add(new Groups(null, null,null,null));
        mObjects.add(getHeadingData().get(0));
        mObjects.add(getVerticalData().get(0));
        return mObjects;
    }

    public static ArrayList<SingleHeadingList> getHeadingData() {
        ArrayList<SingleHeadingList> mTemp = new ArrayList<>();
        mTemp.add(new SingleHeadingList("Colleagues"));
        mTemp.add(new SingleHeadingList("Your Groups"));
        mTemp.add(new SingleHeadingList("Recommended"));
        return mTemp;
    }

    public static ArrayList<Friends> getHorizontalData(List<Friends> friends) {
        friendsGlobal = friends;
        mainAdapter.horizontalDataSetChanged();
        return (ArrayList<Friends>) friendsGlobal;
    }

    //Groups Data.
    public static ArrayList<Groups> getHorizontal2Data(List<Groups> groups) {
        groupsGlobal = groups;
        mainAdapter.horizontal2DataSetChanged();
        return (ArrayList<Groups>) groupsGlobal;
    }

    public static ArrayList<SingleVertical> getVerticalData() {
        ArrayList<SingleVertical> mtemp = new ArrayList<>();
        mtemp.add(new SingleVertical("Novostack"
                ,"https://media.licdn.com/dms/image/C560BAQFUlsj0qEktlA/company-logo_200_200/0?e=2159024400&v=beta&t=5OPMSTSEBAmQwHDfb0kQS2-FiFbJE-4jQDUEK2jG1dQ"
                ,"https://www.officelovin.com/wp-content/uploads/2019/07/forbes-office-bratislava-1.jpg"));
        mtemp.add(new SingleVertical("Google"
                ,"https://lh3.googleusercontent.com/6UgEjh8Xuts4nwdWzTnWH8QtLuHqRMUB7dp24JYVE2xcYzq4HA8hFfcAbU-R-PC_9uA1"
                ,"https://s17026.pcdn.co/wp-content/uploads/sites/9/2017/06/AdobeStock_97559781.jpeg"));
        mtemp.add(new SingleVertical("Amazon"
                ,"https://lh3.googleusercontent.com/proxy/XmXIWU-HraKkFNbvr7F3UdvySIJuT-QvhcTPI34cN4fQM_ZUeV9MHCUNPv65WkUenKVCg3_ZhyuoYVPs_a7Y5JtBrKf_rUr8Emu03GP3Cj_61EBwbZi0-3praKQCH8Ba-8PLTZouqBcn3inUvConTwPnLg4PfiM"
                ,"https://images.unsplash.com/photo-1570126618953-d437176e8c79?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"));
        mtemp.add(new SingleVertical("google"
                ,"https://lh3.googleusercontent.com/6UgEjh8Xuts4nwdWzTnWH8QtLuHqRMUB7dp24JYVE2xcYzq4HA8hFfcAbU-R-PC_9uA1"
                ,"https://images.unsplash.com/photo-1570126618953-d437176e8c79?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"));
        mtemp.add(new SingleVertical("Novostack"
                ,"https://media.licdn.com/dms/image/C560BAQFUlsj0qEktlA/company-logo_200_200/0?e=2159024400&v=beta&t=5OPMSTSEBAmQwHDfb0kQS2-FiFbJE-4jQDUEK2jG1dQ"
                ,"https://s17026.pcdn.co/wp-content/uploads/sites/9/2017/06/AdobeStock_97559781.jpeg"));
        mtemp.add(new SingleVertical("Amazon"
                ,"https://lh3.googleusercontent.com/proxy/XmXIWU-HraKkFNbvr7F3UdvySIJuT-QvhcTPI34cN4fQM_ZUeV9MHCUNPv65WkUenKVCg3_ZhyuoYVPs_a7Y5JtBrKf_rUr8Emu03GP3Cj_61EBwbZi0-3praKQCH8Ba-8PLTZouqBcn3inUvConTwPnLg4PfiM"
                ,"https://www.officelovin.com/wp-content/uploads/2019/07/forbes-office-bratislava-1.jpg"));
        mtemp.add(new SingleVertical("Novostack"
                ,"https://media.licdn.com/dms/image/C560BAQFUlsj0qEktlA/company-logo_200_200/0?e=2159024400&v=beta&t=5OPMSTSEBAmQwHDfb0kQS2-FiFbJE-4jQDUEK2jG1dQ"
                ,"https://images.unsplash.com/photo-1570126618953-d437176e8c79?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"));

        return mtemp;
    }


}
