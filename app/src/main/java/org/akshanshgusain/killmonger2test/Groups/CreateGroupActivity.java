package org.akshanshgusain.killmonger2test.Groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.akshanshgusain.killmonger2test.Network.Company;
import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Groups;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityCreateGroupBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_ID;

public class CreateGroupActivity extends AppCompatActivity implements AdapterContact.OnItemViewClickListener, RestCalls.GetFriendsListI, RestCalls.CreateGroupI {
   private ActivityCreateGroupBinding binding;
    List<SingleFriends> temp = new ArrayList<>();
    RestCalls restCalls = new RestCalls(this);
    private static final String TAG = "CreateGroupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_group );
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
        restCalls.getFriendsList(pref.getString(PREF_KEY_ID,""));
    }

    @Override
    public void clickListener(int position, List<Integer> userIds, SingleFriends contact) {
           try{
               temp.set(position,contact) ;

           }catch(Exception e){

           }
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
        binding.recyclerViewParticipants.setAdapter(new AdapterContact(temp,this));
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
             String userId = pref.getString(PREF_KEY_ID,"");
             //Get the ids of participants.
              String ids = new String();
              int i=0;
              for(SingleFriends tempu : selectedFriends){
                  if(i<selectedFriends.size()-1){
                      ids = ids + ","+tempu.getId();
                  }else{
                      ids = ids +tempu.getId();
                  }
                  i++;
              }
              //Call Create Group API
              Map<String, String> paramsMap = new HashMap<>();
              paramsMap.put("userid",userId );
              paramsMap.put("grouptitle",title );
              paramsMap.put("groupalluserid",ids );
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
