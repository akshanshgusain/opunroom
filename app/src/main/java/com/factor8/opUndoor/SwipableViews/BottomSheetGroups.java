package com.factor8.opUndoor.SwipableViews;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.factor8.opUndoor.Network.RestCalls;
import com.factor8.opUndoor.ProjectConstants;
import com.bumptech.glide.Glide;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.FragmentBottomSheetGroupsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import com.factor8.opUndoor.Groups.UpdateGroupSettings;
import com.factor8.opUndoor.Network.Responses.Feed;

import static android.content.Context.MODE_PRIVATE;


public class BottomSheetGroups extends BottomSheetDialogFragment {

    private Feed.GroupsBean mCurrentGroup;
    private FragmentBottomSheetGroupsBinding binding;

    public static final int VIEW_ALL_USERS = 0;
    public static final int ADD_USERS = 1;
    public static final int REMOVE_USERS = 2;
    public static final int UPDATE_NAME = 3;

    public static final String INTENT_ACTION_TYPE = "action_type";
    public static final String INTENT_GROUP_OBJ = "groupOBJ";
    public static final String INTENT_ADMIN_ID = "adminId";

    private Gson  gson = new Gson();
    public AlertDialog dialogOfDelete;

    public BottomSheetGroups(Feed.GroupsBean currentGroup) {
        this.mCurrentGroup = currentGroup;
    }

    public BottomSheetGroups() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_groups,container, false);
        View view  = binding.getRoot();

        binding.adminName.setText(mCurrentGroup.getAdminname());
        binding.textViewGroupTitle.setText(mCurrentGroup.getGrouptitle());
        binding.textViewGroupParticipants.setText(mCurrentGroup.getNo_of_member()+" participants");

        Glide.with(getActivity()).load(mCurrentGroup.getAdminpicture()).into(binding.adminImage);

        binding.viewAllMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), UpdateGroupSettings.class);
                i.putExtra(INTENT_ACTION_TYPE, VIEW_ALL_USERS);
                i.putExtra(INTENT_GROUP_OBJ,gson.toJson(mCurrentGroup));
              //  i.putExtra(INTENT_ADMIN_ID, mCurrentGroup.)
                startActivity(i);
                dismiss();
            }
        });

        binding.AddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), UpdateGroupSettings.class);
                i.putExtra(INTENT_ACTION_TYPE, ADD_USERS);
                i.putExtra(INTENT_GROUP_OBJ, gson.toJson(mCurrentGroup));
                startActivity(i);
                dismiss();
            }
        });
        binding.RemoveMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), UpdateGroupSettings.class);
                i.putExtra(INTENT_ACTION_TYPE, REMOVE_USERS);
                i.putExtra(INTENT_GROUP_OBJ, gson.toJson(mCurrentGroup));
                startActivity(i);
                dismiss();
            }
        });
        binding.OtherGroupSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), UpdateGroupSettings.class);
                i.putExtra(INTENT_ACTION_TYPE, UPDATE_NAME);
                i.putExtra(INTENT_GROUP_OBJ, gson.toJson(mCurrentGroup));
                startActivity(i);
                dismiss();
            }
        });

        binding.buttonDeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RestCalls restCalls = new RestCalls(getActivity());

                dialogOfDelete =  new AlertDialog.Builder(getActivity())
                        .setTitle("Delete Group")
                        .setMessage("Delete Group "+ mCurrentGroup.getGrouptitle()+" with "+mCurrentGroup.getNo_of_member()+" members?")
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                  dismiss();
                            }
                        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restCalls.deleteGroup(mCurrentGroup.getId());

                    }
                }).show();

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        String currentUserId = pref.getString(ProjectConstants.PREF_KEY_ID, "");
        if(!mCurrentGroup.getGroupadminid().equals(currentUserId)){
            binding.AddMembers.setVisibility(View.GONE);
            binding.RemoveMembers.setVisibility(View.GONE);
            binding.OtherGroupSettings.setVisibility(View.GONE);
            binding.buttonDeleteGroup.setVisibility(View.GONE);
            binding.div24.setVisibility(View.VISIBLE);
        }

    }
}