package org.akshanshgusain.killmonger2test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.akshanshgusain.killmonger2test.Groups.CreateGroupActivity;
import org.akshanshgusain.killmonger2test.LoginRegister.LoginActivity;
import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Groups;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.SendPicture.PreviewActivity;
import org.akshanshgusain.killmonger2test.SwipableViews.CameraFragment;
import org.akshanshgusain.killmonger2test.SwipableViews.ChatFragment;
import org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment;
import org.akshanshgusain.killmonger2test.SwipableViews.SectionPagerAdapter;
import org.akshanshgusain.killmonger2test.ViewStories.StoryViewerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_IS_LOGIN;
import static org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment.getHorizontal2Data;
import static org.akshanshgusain.killmonger2test.SwipableViews.DashBoardFragment.getHorizontalData;


public class MainActivity extends AppCompatActivity implements AdapterHorizontal.Horizontal1ClickListener
        , AdapterHorizontal2.Horizontal2ClickListener, AdapterVertical.VerticalClickListener
        , RestCalls.FeedI {

    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager2 mViewPager;
    private int fragNUm;
    private List<Groups> groupsGlobal = new ArrayList<>();
    private List<Friends> friendsGlobal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragNUm = getIntent().getIntExtra("frag", 0);
        mSectionPagerAdapter = new SectionPagerAdapter(this);
        mViewPager = findViewById(R.id.viewPager_main);
        mViewPager.setOffscreenPageLimit(3);
        setUpViewPager(mViewPager);
        checkLogin();
    }


    private void setUpViewPager(ViewPager2 mViewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(this);
        adapter.addFragment(new ChatFragment(), "Chat");
        adapter.addFragment(new CameraFragment(), "Camera");
        adapter.addFragment(new DashBoardFragment(), "Dashboard");
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        if (fragNUm != 0) {
            mViewPager.setCurrentItem(fragNUm);
        }

    }


    @Override
    public void onHorizontal1ClickListener(int position) {
        if (position == 0) {
            mViewPager.setCurrentItem(1);
        }else{
            //Send storylist to next Viewer:----------
            List<String> stories = friendsGlobal.get(position).getStoryPictures();
            if (stories.size() != 0) {
                Intent i = new Intent(this, StoryViewerActivity.class);
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
            //Send storylist to next Viewer:----------
            List<String> stories = groupsGlobal.get(position).getGroupstories();
            if (stories.size() != 0) {
                Intent i = new Intent(this, StoryViewerActivity.class);
                i.putStringArrayListExtra("stories", (ArrayList<String>) stories);
                startActivity(i);
            } else {
                Toast.makeText(this, "No Stories to Show", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void verticalClickListener(int position) {

    }

    private void checkLogin() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        if (!pref.getBoolean(PREF_KEY_IS_LOGIN, false)) {
            Intent p = new Intent(MainActivity.this, LoginActivity.class);
            p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(p);
            finish();
        }
    }

    //Feed Rest Call Methods: ------------- start
    @Override
    public void feedResponse2(Map<String, String> response) {

    }

    @Override
    public void feedResponseStories(List<Friends> stories) {
        this.friendsGlobal = new ArrayList<>();
        this.friendsGlobal.add(new Friends("null", "null", "null", "null", "null", "null", "null", "null", null));
        this.friendsGlobal.addAll(stories);
        getHorizontalData(this.friendsGlobal);
    }

    @Override
    public void feedResponseGroups2(List<Groups> groups) {
        this.groupsGlobal = new ArrayList<>();
        this.groupsGlobal.add(new Groups(null, null, null, null));
        this.groupsGlobal.addAll(groups);
        getHorizontal2Data(this.groupsGlobal);


    }

    @Override
    public void feedErrorRequest2(Map<String, String> response) {

        Log.d("FeedErrorResponse", "feedErrorRequest2:Exception " + response.get("exception"));
        Log.d("FeedErrorResponse", "feedErrorRequest2:Error " + response.get("VolleyError"));
    }
    //Feed Rest Call Methods: ------------- End
}
