package com.factor8.opUndoor.Groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.factor8.opUndoor.Network.Company;
import com.factor8.opUndoor.Network.Friends;
import com.factor8.opUndoor.Network.RestCalls;
import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.Network.Friends2;
import com.factor8.opUndoor.Network.GroupMembers;
import com.factor8.opUndoor.Network.Groups;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.ActivityCreateGroupBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity implements AdapterContact.OnItemViewClickListener, RestCalls.GetFriendsListI, RestCalls.CreateGroupI {
   private ActivityCreateGroupBinding binding;
    List<SingleFriends> temp = new ArrayList<>();
    RestCalls restCalls = new RestCalls(this);
    private static final String TAG = "CreateGroupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group );
        binding.floatingActionButtonNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                 createGroup();
           }
       });
       binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
    }



    @Override
    protected void onResume() {
        super.onResume();
        //Restcall - Get All the Contacts
        SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        restCalls.getFriendsList(pref.getString(ProjectConstants.PREF_KEY_ID,""));
    }

    @Override
    public void clickListener(int position, SingleFriends contact) {
           try{
               temp.set(position,contact) ;
               Log.d(TAG, "clickListener: ID: "+ temp.get(position).id);

           }catch(Exception e){

           }
    }

    @Override
    public void clickListenerAddUSer(int position, Friends2.FriendsBean friend) {

    }

    @Override
    public void clickListenerDeleteUSer(int position, GroupMembers.GroupsBean.MemberBean friend) {

    }

    @Override
    public void response(Map<String, String> response) {

    }

    @Override
    public void errorRequest(Map<String, String> response) {
        Log.d(TAG, "errorRequest: Exception get List  :" + response.get("exception"));
        Log.d(TAG, "errorRequest: VolleyError get List  :" + response.get("VolleyError"));
    }

    @Override
    public void responseList(List<Friends> friendsList) {

        for(Friends friend : friendsList){
              temp.add(new SingleFriends(friend.getId(), friend.getF_name(), friend.getL_name(), friend.getUsername()
              , friend.getEmail(), friend.getPassword(),friend.getNetwork(), friend.getPicture()));
        }
        binding.recyclerViewParticipants.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewParticipants.setAdapter(new AdapterContact(temp,this,0));
    }

    @Override
    public void responseListGroups(List<Groups> groupsList) {

    }

    @Override
    public void responseListCompany(List<Company> companyList) {

    }


    private void createGroup() {
          List<SingleFriends> selectedFriends = new ArrayList<>();
          for(SingleFriends tempu : temp){
                if(tempu.isSelected){
                    selectedFriends.add(tempu);
                }
          }

          if (selectedFriends.size() == 0){
              Toast.makeText(this, "Please Select Participants", Toast.LENGTH_SHORT).show();
          }else if(binding.editTextGroupName.getText().toString().trim().isEmpty()){
              Toast.makeText(this, "Please Enter the title ", Toast.LENGTH_SHORT).show();
          }
          else if(binding.editTextGroupName.getText().toString().trim().length() <= 4){
              Toast.makeText(this, "Title Should be at least 5 Characters ", Toast.LENGTH_SHORT).show();
          }else{
                //Get the Title of the group:
              String title = binding.editTextGroupName.getText().toString().trim();
              //Get the userId of the admin:
             SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
             String userId = pref.getString(ProjectConstants.PREF_KEY_ID,"");
             //Get the ids of participants.
              StringBuilder ids = new StringBuilder(new String());
              int i=0;
              for(SingleFriends tempu : selectedFriends){
                  if(i<selectedFriends.size()){
                      if(ids.length() == 0){
                          ids = new StringBuilder(tempu.getId());
                      }else{
                          ids.append(",").append(tempu.getId());
                      }
                  }
                  i++;
              }

              //Call Create Group API
              Log.d(TAG, "createGroup: USerIDs: "+ ids);
              Map<String, String> paramsMap = new HashMap<>();
              paramsMap.put("userid",userId );
              paramsMap.put("grouptitle",title );
              paramsMap.put("groupalluserid", ids.toString());
              restCalls.createGroup(paramsMap);
          }
    }

    @Override
    public void response2(Map<String, String> response) {
        if(response.get("status").equals("1")){
            Toast.makeText(this, "Group Created Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }else if(response.get("status").equals("0")){
            Toast.makeText(this, "Such Group already exist", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void errorRequest2(Map<String, String> response) {
        Log.d(TAG, "errorRequest: Exception  Create Group ::" + response.get("exception"));
        Log.d(TAG, "errorRequest: VolleyError Create Group ::" + response.get("VolleyError"));
    }
}
