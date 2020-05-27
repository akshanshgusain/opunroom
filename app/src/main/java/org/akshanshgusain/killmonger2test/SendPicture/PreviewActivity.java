package org.akshanshgusain.killmonger2test.SendPicture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Groups;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityPreviewBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PICTURE_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_ID;
import static org.akshanshgusain.killmonger2test.SendPicture.AdapterContacts.GROUP_TYPE;
import static org.akshanshgusain.killmonger2test.SendPicture.AdapterContacts.SELF_TYPE;

public class PreviewActivity extends AppCompatActivity implements
        RestCalls.GetFriendsListI, AdapterContacts.AdapterClickListener, BottomSheetFragment.SendButtonClick, RestCalls.CreateStoryI {

    private ActivityPreviewBinding binding;
    private static final String TAG = "PreviewTag";
    BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
    private LinkedHashSet<SingleContact> selectedItems = new LinkedHashSet<>();
    SharedPreferences pref;
    RestCalls restCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview);
        String filePath = getIntent().getStringExtra("imagePreview");
        Glide.with(this).load(filePath).into(binding.imageViewPreview);

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                //call the service to get friend List
                pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
                restCalls = new RestCalls(PreviewActivity.this);
                restCalls.getFriendsList(pref.getString(PREF_KEY_ID, ""));
            }
        });
    }

    @Override
    public void response(Map<String, String> response) {

    }

    @Override
    public void errorRequest(Map<String, String> response) {

    }

    @Override
    public void responseList(List<Friends> friendsList) {
//        if (friendsList != null) {
//
//            bottomSheetFragment.dataFromParent(friendsList);
//        }
    }

    @Override
    public void responseListGroups(List<Groups> groupsList) {
        if (groupsList != null) {

            bottomSheetFragment.dataFromParent(groupsList);
        }
    }


    @Override
    public void adapterClickListener(int position, String id, SingleContact model) {
        selectedItems.add(model);
    }

    @Override
    public void sendButtonClick() {
        int count =0;
        for (SingleContact temp : selectedItems) {
            if (temp.isSelected) {
                 count++;
            }
        }
        if(count==0 ){
            Toast.makeText(this, "Please Select target", Toast.LENGTH_SHORT).show();
        }else{
            String selfIds = new String();
            String groupIds = new String();

            for (SingleContact temp : selectedItems) {
                if (temp.isSelected) {
                    switch (temp.getType()) {
                        case SELF_TYPE: {
                            selfIds = temp.getUserId();
                        }
                        break;
                        case GROUP_TYPE: {
                            groupIds = groupIds + "," + temp.getUserId();
                        }
                        break;
                    }
                }
            }
            //Call createStory API: >>>>
            Bundle bundle = new Bundle();
            bundle.putString("userid", pref.getString(PREF_KEY_ID, ""));
            bundle.putString("selfid", selfIds);
            bundle.putString("friends", "");
            bundle.putString("groups", groupIds);
            bundle.putByteArray(INTENT_PICTURE_WORKSPACE, getFileDataFromDrawable(null));

            restCalls.createStory(bundle);
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            bitmap = ((BitmapDrawable) binding.imageViewPreview.getDrawable()).getBitmap();

        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        Log.d(TAG, "getFileDataFromDrawable: image: " + byteArrayOutputStream.toByteArray());
        return byteArrayOutputStream.toByteArray();

    }

    @Override
    public void responseCS(Map<String, String> response) {
                    if(response.get("status").equals("1")){
                        Toast.makeText(this, "Story Sent!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(this, "SomeError Occurred", Toast.LENGTH_SHORT).show();
                    }
    }

    @Override
    public void errorRequestCS(Map<String, String> response) {
        Toast.makeText(this, ""+response.toString(), Toast.LENGTH_SHORT).show();
    }
}
