package org.akshanshgusain.killmonger2test.SendPicture;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.akshanshgusain.killmonger2test.Groups.AdapterContact;
import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Groups;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;

import java.io.IOError;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.*;
import static org.akshanshgusain.killmonger2test.SendPicture.AdapterContacts.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment   {
    private RecyclerView  recyclerView;
    private List<SingleContact> groups;
    private Button mSend;
    private SendButtonClick sendButtonClick;

    public interface SendButtonClick{
        public void sendButtonClick();
    }
    public BottomSheetFragment() {

    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendButtonClick = (SendButtonClick)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout_1, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_contacts);
        mSend = view.findViewById(R.id.button_send);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 sendButtonClick.sendButtonClick();
            }
        });
        return view;
    }

     void dataFromParent(List<Groups> list){

        List<SingleContact> temp = new ArrayList<>();

         SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        String  name =  pref.getString(PREF_KEY_F_NAME,"")+" "+ pref.getString(PREF_KEY_L_NAME,"");

         temp.add(new SingleContact("", "2","Add to your story",HEADING_TYPE));
         temp.add(new SingleContact(pref.getString(PREF_KEY_PICTURE,""), pref.getString(PREF_KEY_ID,""),name,SELF_TYPE));
        temp.add(new SingleContact("", "2","Your Groups",HEADING_TYPE));
        for(Groups groups: list){
            temp.add(new SingleContact(""
                      , groups.getId()
                    ,groups.getGrouptitle()
                    ,GROUP_TYPE));
        }
         groups = temp;
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         recyclerView.setAdapter(new AdapterContacts(groups, getActivity()));
    }


}
