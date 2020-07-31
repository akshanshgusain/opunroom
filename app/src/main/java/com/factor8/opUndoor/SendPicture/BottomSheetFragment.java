package com.factor8.opUndoor.SendPicture;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.factor8.opUndoor.Network.Friends2;
import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {
    private RecyclerView recyclerView;
    private List<SingleContact> groups = new ArrayList<>();
    private List<SingleContact> companies = new ArrayList<>();
    private Button mSend;
    private SendButtonClick sendButtonClick;
    private ProgressBar progressBar;
    private static final String TAG = "BottomSheetFragment";

    public interface SendButtonClick {
        public void sendButtonClick();
    }

    public BottomSheetFragment() {

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendButtonClick = (SendButtonClick) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_layout_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView_contacts);
        mSend = view.findViewById(R.id.button_send);
        progressBar = view.findViewById(R.id.progress);
        DrawableCompat.setTint(progressBar.getIndeterminateDrawable(),getActivity().getResources().getColor(R.color.colorAccent));
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendButtonClick.sendButtonClick();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    void dataFromParent(Friends2 friends2) {
        if (friends2.getStatus() == 1) {
            List<SingleContact> temp = new ArrayList<>();
            try{
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
                String name = pref.getString(ProjectConstants.PREF_KEY_F_NAME, "") + " " + pref.getString(ProjectConstants.PREF_KEY_L_NAME, "");

                temp.add(new SingleContact("", "2", "Add to your Company's Story", AdapterContacts.HEADING_TYPE));
                for (Friends2.CompanyBean groups : friends2.getCompany()) {
                    temp.add(new SingleContact(groups.getDisplay_picture()
                            , groups.getId()
                            , groups.getName()
                            , AdapterContacts.COMPANY_TYPE));
                }
                temp.add(new SingleContact("", "2", "Add to your story", AdapterContacts.HEADING_TYPE));
                temp.add(new SingleContact(pref.getString(ProjectConstants.PREF_KEY_PICTURE, ""), pref.getString(ProjectConstants.PREF_KEY_ID, ""), name, AdapterContacts.SELF_TYPE));
                temp.add(new SingleContact("", "2", "Your Groups", AdapterContacts.HEADING_TYPE));
                for (Friends2.GroupsBean groups : friends2.getGroups()) {
                    temp.add(new SingleContact(""
                            , groups.getId()
                            , groups.getGrouptitle()
                            , AdapterContacts.GROUP_TYPE));
                }
                groups = temp;

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new AdapterContacts(groups, getActivity()));

                progressBar.setVisibility(View.GONE);
            }catch (Exception e){
                Log.e(TAG, "dataFromParent: ",e );
                progressBar.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }
}
