package org.akshanshgusain.killmonger2test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.theartofdev.edmodo.cropper.CropImage;

import org.akshanshgusain.killmonger2test.Groups.CreateGroupActivity;
import org.akshanshgusain.killmonger2test.LoginRegister.LoginActivity;
import org.akshanshgusain.killmonger2test.Network.Company;
import org.akshanshgusain.killmonger2test.Network.Company_Stories;
import org.akshanshgusain.killmonger2test.Network.Feed;
import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Group_Stories;
import org.akshanshgusain.killmonger2test.Network.Groups;
import org.akshanshgusain.killmonger2test.Network.NewsChannel;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.SendPicture.PreviewActivity;
import org.akshanshgusain.killmonger2test.SwipableViews.BottomSheetGroups;
import org.akshanshgusain.killmonger2test.SwipableViews.CameraFragment;
import org.akshanshgusain.killmonger2test.SwipableViews.ChatFragment;
import org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment;
import org.akshanshgusain.killmonger2test.SwipableViews.SectionPagerAdapter;
import org.akshanshgusain.killmonger2test.ViewStories.StoryViewerActivity;
import org.akshanshgusain.killmonger2test.ViewStories.StoryViewer_2_Activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_IS_LOGIN;
import static org.akshanshgusain.killmonger2test.SwipableViews.CameraFragment.MEDIA_TYPE_PICTURE;
import static org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment.getHorizontal2Data;
import static org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment.getHorizontalData;
import static org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment.getVertical2Data;
import static org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment.getVerticalData;
import static org.akshanshgusain.killmonger2test.ViewStories.StoryViewerActivity.*;


public class MainActivity extends AppCompatActivity implements AdapterHorizontal.Horizontal1ClickListener
        , AdapterHorizontal2.Horizontal2ClickListener, AdapterVertical.VerticalClickListener, AdapterVertical2.Vertical2ClickListener
        , RestCalls.FeedI, RestCalls.GetNewsStoryI, View.OnClickListener, MainAdapter.EnableDisableScrollInViewPager
        , RestCalls.DeleteGroupI {

    private ViewPager2 mViewPager;
    DashBoardFragment dashBoardFragment;

    private ImageView mLeftButton, mRightButton;
    private int fragNUm;
    private List<Feed.GroupsBean> groupsGlobal = new ArrayList<>();
    private List<Feed.FriendsBean> friendsGlobal = new ArrayList<>();
    private List<Feed.CompanyBean> companiesGlobal = new ArrayList<>();
    private List<NewsChannel> newsChannels = new ArrayList<>();

    public static final int FRAGMENT_CHAT = 0;
    public static final int FRAGMENT_CAMERA = 1;
    public static final int FRAGMENT_DASHBOARD = 2;
    public int FRAGMENT_CURRENT = 1;
    private static final String TAG = "MainActivity";

    BottomSheetGroups bottomSheetGroups;
    private List<Feed.CompanyBean> networkGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.setDarkMode(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorBlack));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragNUm = getIntent().getIntExtra("frag", 0);
        mViewPager = findViewById(R.id.viewPager_main);
        mLeftButton = findViewById(R.id.imageView_leftButton);
        mLeftButton.setOnClickListener(this);
        Log.i("TESTING", "CREATED: " + getClass().getSimpleName() + " -- TASK ID: " + getTaskId());

        mRightButton = findViewById(R.id.imageView_rightButton);
        mRightButton.setOnClickListener(this);
        mViewPager.setOffscreenPageLimit(3);
        //checkLogin();
        setUpViewPager(mViewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    private void setUpViewPager(ViewPager2 mViewPager) {
         dashBoardFragment = new DashBoardFragment();
        SectionPagerAdapter adapter = new SectionPagerAdapter(this);
        adapter.addFragment(new ChatFragment(), "Chat");
        adapter.addFragment(new CameraFragment(), "Camera");
        adapter.addFragment(dashBoardFragment, "Dashboard");

        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        if (fragNUm != 0) {
            mViewPager.setCurrentItem(fragNUm);
        }


        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == FRAGMENT_CAMERA) {
                    mLeftButton.setVisibility(View.VISIBLE);
                    mRightButton.setVisibility(View.VISIBLE);
                    mLeftButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_chat));
                    mRightButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_dashboard));
                }
                if (position == FRAGMENT_CHAT) {
                    mLeftButton.setVisibility(View.GONE);
                    mRightButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_shutter));

                }
                if (position == FRAGMENT_DASHBOARD) {
                    //dashBoardFragment.CustomOnResumeCallForDashBoard();
                    mRightButton.setVisibility(View.GONE);
                    mLeftButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_shutter));

                }
            }
        });
    }


    @Override
    public void onHorizontal1ClickListener(int position) {
        if (position == 0) {
            mViewPager.setCurrentItem(1);
        } else {
            //Send storylist to next Viewer:----------
            List<String> stories = new ArrayList<>();

            Feed.FriendsBean temp = friendsGlobal.get(position);
                if(temp.getStorypicture()!=null){
                     for(int i =0; i<temp.getStorypicture().size();i++){
                         stories.add(temp.getStorypicture().get(i).getStorypicture());
                     }

                }

            if (stories.size() != 0) {
                Intent i = new Intent(this, StoryViewerActivity.class);
                i.putExtra("type", STORY_STATUS);
                i.putExtra("statusId", friendsGlobal.get(position).getId());
                i.putStringArrayListExtra("stories", (ArrayList<String>) stories);
                startActivity(i);
            } else {
                Toast.makeText(this, "No Stories to Show", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onHorizontal2ClickListener(int position) {
        if (position == 0) {
            startActivity(new Intent(this, CreateGroupActivity.class));
        } else {
            //Send story list to next Viewer:----------
            List<Feed.GroupsBean.GrouppicturesBean> stories = groupsGlobal.get(position).getGrouppictures();
            Gson gson = new Gson();
            if (stories.size() != 0) {
                Intent i = new Intent(this, StoryViewerActivity.class);
                i.putExtra("type", STORY_GROUPS);
                i.putExtra("stories", gson.toJson(stories));
                startActivity(i);
            } else {
                Toast.makeText(this, "No Stories to Show", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onHorizontal2LongClickListener(int position) {
        if (position == 0) {
            //Do nothing
        } else {
            //Vibrate
            Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(100);
            }
            //Get Group Details
            Feed.GroupsBean currentGroup = groupsGlobal.get(position);
            //Open Bottom Sheet Groups
            bottomSheetGroups = new BottomSheetGroups(currentGroup);
            bottomSheetGroups.show(getSupportFragmentManager(), "bottomSheetGroups");
        }
    }

    @Override
    public void verticalClickListener(int position) {
        List<Feed.CompanyBean.StorypictureBean> stories = networkGlobal.get(position).getStorypicture();
        Gson gson = new Gson();
        if (stories.size() != 0) {
            Intent i = new Intent(this, StoryViewerActivity.class);
            i.putExtra("type", STORY_COMPANY);
            i.putExtra("stories", gson.toJson(stories));
            startActivity(i);
        } else {
            Toast.makeText(this, "No Stories to Show", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void vertical2ClickListener(int position) {
        NewsChannel channel = newsChannels.get(position);
        Gson gson = new Gson();
        if (channel.getArticles().size() != 0) {
            Intent i = new Intent(this, StoryViewer_2_Activity.class);
            i.putExtra("type", STORY_COMPANY);
            i.putExtra("stories", gson.toJson(channel));
            startActivity(i);
        } else {
            Toast.makeText(this, "No Stories to Show", Toast.LENGTH_SHORT).show();
        }


    }


    private void checkLogin() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        if (!pref.getBoolean(PREF_KEY_IS_LOGIN, false)) {
            Intent p = new Intent(MainActivity.this, LoginActivity.class);
            p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(p);
            finish();
        }
    }

    //Feed Rest Call Methods: ------------- start
    /*Changes Here---------*/

    @Override
    public void feedResponse(Feed feed) {
        //Friends
        this.friendsGlobal = new ArrayList<>();
        this.friendsGlobal.add(new Feed.FriendsBean());
        this.friendsGlobal.addAll(feed.getFriends());
        getHorizontalData(this.friendsGlobal);

        //Groups
        this.groupsGlobal = new ArrayList<>();
        this.groupsGlobal.add(new Feed.GroupsBean());
        this.groupsGlobal.addAll(feed.getGroups());
        getHorizontal2Data(this.groupsGlobal);

//        //companies
//        this.companiesGlobal = new ArrayList<>();
//        this.companiesGlobal = feed.getCompany();
//        Log.d("actiMain", "feedResponseCompanies: " + companiesGlobal.size());
//        getVerticalData(this.companiesGlobal);

        //Network
        this.networkGlobal = new ArrayList<>();
        this.networkGlobal = feed.getNetwork();
        Log.d("actiMain", "feedResponseCompanies: " + networkGlobal.size());
        getVerticalData(this.networkGlobal);
    }

    @Override
    public void feedErrorResponse(Map<String, String> errorMap) {
        Log.d("FeedErrorResponse", "feedErrorRequest2:Exception " + errorMap.get("exception"));
        Log.d("FeedErrorResponse", "feedErrorRequest2:Error " + errorMap.get("VolleyError"));
    }


    /*Changes Here---------*/
    @Override
    public void responseCS(Map<String, String> response) {

    }

    @Override
    public void responseArticles(NewsChannel feed) {
        if (newsChannels.size() != 3) {
            newsChannels.add(feed);
            getVertical2Data(newsChannels);
        }

    }

    @Override
    public void errorRequestCS(Map<String, String> response) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_leftButton: {
                if (FRAGMENT_CURRENT == FRAGMENT_CAMERA) {
                    mViewPager.setCurrentItem(FRAGMENT_CHAT);
                    FRAGMENT_CURRENT = FRAGMENT_CHAT;

                    mLeftButton.setVisibility(View.GONE);
                    mRightButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_shutter));
                }

                if (FRAGMENT_CURRENT == FRAGMENT_DASHBOARD) {
                    mViewPager.setCurrentItem(FRAGMENT_CAMERA);
                    FRAGMENT_CURRENT = FRAGMENT_CAMERA;

                    mLeftButton.setVisibility(View.VISIBLE);
                    mRightButton.setVisibility(View.VISIBLE);
                    mLeftButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_chat));
                    mRightButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_dashboard));
                }
            }
            break;
            case R.id.imageView_rightButton: {
                if (FRAGMENT_CURRENT == FRAGMENT_CAMERA) {
                    mViewPager.setCurrentItem(FRAGMENT_DASHBOARD);
                    FRAGMENT_CURRENT = FRAGMENT_DASHBOARD;
                    mRightButton.setVisibility(View.GONE);


                    mLeftButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_shutter));
                }
                if (FRAGMENT_CURRENT == FRAGMENT_CHAT) {
                    mViewPager.setCurrentItem(FRAGMENT_CAMERA);
                    FRAGMENT_CURRENT = FRAGMENT_CAMERA;

                    mLeftButton.setVisibility(View.VISIBLE);
                    mRightButton.setVisibility(View.VISIBLE);
                    mLeftButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_chat));
                    mRightButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_dashboard));
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TESTING", "DESTROYED: " + getClass().getSimpleName() + " -- TASK ID: " + getTaskId());
    }

    @Override
    public void enableDisableScrollInViewPager(boolean enable) {
        Log.d("enableDisable", "enableDisableScrollInViewPager: "+ enable);
         mViewPager.setUserInputEnabled(enable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                File file = new File(resultUri.getPath());

                try {
                    File pictureDPAth = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            + File.separator + "OpunRoom", "OpunRoomCom");
                    File compressedImage = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(pictureDPAth.getAbsolutePath())
                            .compressToFile(file);

                    Intent i = new Intent(this, PreviewActivity.class);
                    i.putExtra("type", MEDIA_TYPE_PICTURE);
                    i.putExtra("mediaPreview", compressedImage.getAbsolutePath());
                    startActivity(i);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onFileReady: " + e);
                }





            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

            }
        }
    }

    @Override
    public void responseDeleteG(Map<String, String> response) {
           if(response.get("status").equals("1")){
               //Group Delete
               bottomSheetGroups.dismiss();
               dashBoardFragment.onResume();
           }else{
               Toast.makeText(this, "Problem Deleting Group", Toast.LENGTH_SHORT).show();
               bottomSheetGroups.dismiss();
               bottomSheetGroups.dialogOfDelete.dismiss();
           }
    }

    @Override
    public void errorRequestDeleteG(Map<String, String> response) {
        Toast.makeText(this, ""+response.get("VolleyError"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+response.get("exception"), Toast.LENGTH_SHORT).show();
    }


}
