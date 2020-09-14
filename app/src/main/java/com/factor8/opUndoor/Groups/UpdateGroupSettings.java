package com.factor8.opUndoor.Groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.factor8.opUndoor.Network.RestCalls;
import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.SwipableViews.BottomSheetGroups;
import com.factor8.opUndoor.databinding.ActivityUpdateGroupSettingsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.factor8.opUndoor.Network.Responses.Feed;
import com.factor8.opUndoor.Network.Responses.Friends2;
import com.factor8.opUndoor.Network.Responses.GroupMembers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static android.view.View.GONE;

public class UpdateGroupSettings extends AppCompatActivity implements
        RestCalls.GetGroupMembersI
        , AdapterContact.OnItemViewClickListener
        , RestCalls.GetFriendsList2I,
        RestCalls.UpdateGroupI {

    private ActivityUpdateGroupSettingsBinding binding;
    private int ACTION_TYPE_INT = 0;
    private GroupMembers mGroupMemberObject = new GroupMembers();
    private Friends2 mFriendsObject = new Friends2();
    RestCalls restCalls;
    private static final String TAG = "UpdateGroupSettings";

    private Gson gson = new Gson();
    private Feed.GroupsBean groupsBean;

    private LinkedHashSet<Friends2.FriendsBean> mSelectedFriendsToAdd = new LinkedHashSet<>();
    private LinkedHashSet<GroupMembers.GroupsBean.MemberBean> mSelectedFriendsToDelete = new LinkedHashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_group_settings);
        ACTION_TYPE_INT = getIntent().getIntExtra(BottomSheetGroups.INTENT_ACTION_TYPE, 0);

        Type group_type = new TypeToken<Feed.GroupsBean>() {
        }.getType();

        groupsBean = gson.fromJson(getIntent().getStringExtra(BottomSheetGroups.INTENT_GROUP_OBJ), group_type);

        Log.d(TAG, "onCreate: GroupId:" + groupsBean.getId());
        restCalls = new RestCalls(this);
        prepareView();

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.floatingActionButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ACTION_TYPE_INT == BottomSheetGroups.ADD_USERS) {
                    addUsersToGroup();
                } else if (ACTION_TYPE_INT == BottomSheetGroups.REMOVE_USERS) {
                    removeUsersFromGroup();
                } else if (ACTION_TYPE_INT == BottomSheetGroups.UPDATE_NAME) {
                    String newName = binding.editTextGroupName.getText().toString().trim();
                    if (newName.isEmpty()) {
                        Toast.makeText(UpdateGroupSettings.this, "Please Enter the title ", Toast.LENGTH_SHORT).show();
                    } else if (binding.editTextGroupName.getText().toString().trim().length() <= 4) {
                        Toast.makeText(UpdateGroupSettings.this, "Title Should be at least 5 Characters ", Toast.LENGTH_SHORT).show();
                    } else {
                        updateName(newName);
                    }

                }
            }
        });


    }


    private void prepareView() {
        binding.progress.setVisibility(View.VISIBLE);
        if (ACTION_TYPE_INT == BottomSheetGroups.UPDATE_NAME) {

            binding.appBarTextView.setText("Change Name");
            binding.editTextGroupName.setVisibility(View.VISIBLE);
            binding.floatingActionButtonNext.setText("Confirm");
            binding.editTextGroupName.setText(groupsBean.getGrouptitle());
            binding.progress.setVisibility(View.GONE);

        } else if (ACTION_TYPE_INT == BottomSheetGroups.VIEW_ALL_USERS) {

            binding.appBarTextView.setText("All Participants");
            binding.floatingActionButtonNext.setVisibility(GONE);
            restCalls.getGroupMembers(groupsBean.getId());

        } else if (ACTION_TYPE_INT == BottomSheetGroups.ADD_USERS) {

            binding.appBarTextView.setText("Add Participants");
            binding.heading.setVisibility(View.VISIBLE);
            binding.floatingActionButtonNext.setText("Add");

            restCalls.getGroupMembers(groupsBean.getId());
        } else if (ACTION_TYPE_INT == BottomSheetGroups.REMOVE_USERS) {

            binding.appBarTextView.setText("Remove Participants");
            binding.heading.setVisibility(View.VISIBLE);
            binding.floatingActionButtonNext.setText("Remove");

            restCalls.getGroupMembers(groupsBean.getId());
        }
    }

    @Override
    public void responseGetGroupMembers(GroupMembers response) {
        mGroupMemberObject = response;
        Log.d(TAG, "responseGetGroupMembers: size of response: " + response.getGroups().get(0).getMember().size());

        if (ACTION_TYPE_INT == BottomSheetGroups.VIEW_ALL_USERS) {
            binding.recyclerViewParticipants.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerViewParticipants.setAdapter(new AdapterContact(mGroupMemberObject, this, 1));
            binding.progress.setVisibility(View.GONE);
        } else if (ACTION_TYPE_INT == BottomSheetGroups.ADD_USERS) {
            //Show Friends who are'nt already members of Groups
            SharedPreferences pref = this.getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
            restCalls.getFriendsList2(pref.getString(ProjectConstants.PREF_KEY_ID, "0"));

        } else if (ACTION_TYPE_INT == BottomSheetGroups.REMOVE_USERS) {

            binding.recyclerViewParticipants.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerViewParticipants.setAdapter(new AdapterContact(mGroupMemberObject, this, 3));
            binding.progress.setVisibility(View.GONE);

        }

    }

    @Override
    public void errorRequestGetGroupMembers(Map<String, String> response) {

    }

    @Override
    public void response(Friends2 response) {//Show Friends who are'nt already members of Groups
        this.mFriendsObject = response;
        if (ACTION_TYPE_INT == BottomSheetGroups.ADD_USERS) {
            ArrayList<Friends2.FriendsBean> friendsList = (ArrayList<Friends2.FriendsBean>) mFriendsObject.getFriends();
            ArrayList<GroupMembers.GroupsBean.MemberBean> membersList = (ArrayList<GroupMembers.GroupsBean.MemberBean>) mGroupMemberObject.getGroups().get(0).getMember();

            ArrayList<String> membersIds = new ArrayList<>();
            for (GroupMembers.GroupsBean.MemberBean friend : membersList) {
                membersIds.add(String.valueOf(friend.getId()));
            }

            ArrayList<Friends2.FriendsBean> newFriendsList = new ArrayList<>();

            for (Friends2.FriendsBean temp : friendsList) {
                if (!membersIds.contains(temp.getId())) {
                    newFriendsList.add(temp);
                }
            }
            binding.recyclerViewParticipants.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerViewParticipants.setAdapter(new AdapterContact(newFriendsList, this, 2));
            binding.progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void errorRequest(Map<String, String> response) {

    }

    //Adapter ClickListeners

    @Override
    public void clickListener(int position, SingleFriends contact) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    } //NO Use

    @Override
    public void clickListenerAddUSer(int position, Friends2.FriendsBean friend) {
        mSelectedFriendsToAdd.add(friend);
    }

    @Override
    public void clickListenerDeleteUSer(int position, GroupMembers.GroupsBean.MemberBean friend) {
        mSelectedFriendsToDelete.add(friend);

    }

    private void addUsersToGroup() {
        String groupUsers = groupsBean.getGroupuserid();
        StringBuilder newIds = new StringBuilder(new String());

        for (Friends2.FriendsBean tempId : mSelectedFriendsToAdd) {
            if (tempId.isSelected()) {
                if (newIds.length() == 0) {
                    newIds = new StringBuilder(tempId.getId());
                } else {
                    newIds.append(",").append(tempId.getId());
                }
            }

        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("groupid", groupsBean.getId());
        paramMap.put("userid", groupsBean.getGroupadminid());
        paramMap.put("grouptitle", groupsBean.getGrouptitle());
        paramMap.put("groupalluserid", groupUsers + "," + newIds);
        callUpdateAPI(paramMap);
    }

    private void removeUsersFromGroup() {
        String groupUsers = groupsBean.getGroupuserid();
        ArrayList<String> currentGroupMembers = new ArrayList<>(Arrays.asList(groupUsers.split(",")));
        ArrayList<String> selectedmembers = new ArrayList<>();

        for (GroupMembers.GroupsBean.MemberBean temp : mSelectedFriendsToDelete) {
            if (temp.isSelected()) {
                selectedmembers.add(String.valueOf(temp.getId()));
            }
        }
        currentGroupMembers.removeAll(selectedmembers);
        String op = TextUtils.join(",", currentGroupMembers);

        Log.d(TAG, "removeUsersFromGroup: New IDs After REmoval " + op);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("groupid", groupsBean.getId());
        paramMap.put("userid", groupsBean.getGroupadminid());
        paramMap.put("grouptitle", groupsBean.getGrouptitle());
        paramMap.put("groupalluserid", op);
        callUpdateAPI(paramMap);
    }


    private void updateName(String newName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("groupid", groupsBean.getId());
        paramMap.put("userid", groupsBean.getGroupadminid());
        paramMap.put("grouptitle", newName);
        paramMap.put("groupalluserid", groupsBean.getGroupuserid());
        callUpdateAPI(paramMap);
    }


    private void callUpdateAPI(Map<String, String> paramMap) {
        restCalls.updateGroup(paramMap);
    }


    @Override
    public void responseUpdateGroup(Map<String, String> response) {
        if (response.get("status").equals("1")) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void errorUpdateGroup(Map<String, String> error) {

    }
}