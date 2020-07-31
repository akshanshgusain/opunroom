package com.factor8.opUndoor.ViewStories;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.factor8.opUndoor.Application;
import com.factor8.opUndoor.Network.Company;
import com.factor8.opUndoor.Network.Friends;
import com.factor8.opUndoor.Network.RestCalls;
import com.factor8.opUndoor.ProjectConstants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.ActivityStoryViewerBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.factor8.opUndoor.Network.Feed;
import com.factor8.opUndoor.Network.Groups;




import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class StoryViewerActivity extends SwipeDismissBaseActivity implements StoriesProgressView.StoriesListener, RestCalls.GetFriendsListI {
    public static final String STORY_STATUS = "status_story";
    public static final String STORY_GROUPS = "group_story";
    public static final String STORY_COMPANY = "company_story";

    private ActivityStoryViewerBinding binding;
    ArrayList<Feed.GroupsBean.GrouppicturesBean> stories;
    ArrayList<Feed.CompanyBean.StorypictureBean> companyStories;
    ArrayList<String> statuses;
    String statusId;
    private static final String TAG = "ViewerStory";
    public static final String URL = "http://dass.io/oppo/app/story/image/";
    List<Friends> friendsList;

    String intentType;
    SharedPreferences pref;

    public  StoriesProgressView storiesProgressView;
    private ImageView image;

    private int counter = 0;
    boolean isPaused = false;


    long pressTime = 0L;
    long limit = 500L;
    Gson gson = new Gson();

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
                //return false;
            }
            return false;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_story_viewer);
        storiesProgressView = findViewById(R.id.stories);
        pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        getUsersData();
        image = findViewById(R.id.imageView_story);

        // bind reverse view
        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);


    }


    private void getUsersData() {

        RestCalls restCalls = new RestCalls(this);
        restCalls.getFriendsList(pref.getString(ProjectConstants.PREF_KEY_ID, ""));
        binding.progressBarLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNext() {
        Log.d("afakjf", "onNext:  called" + counter);
        setImage(++counter);
    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;
        setImage(--counter);
    }

    @Override
    public void onComplete() {
        finish();
    }

    @Override
    protected void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }

    void setImage(int pos) {


        //User Details in GROUPS
        if (intentType.equals(STORY_GROUPS)) {
            String userId = stories.get(pos).getUserid();
            Friends user = null;
            for (Friends temp : friendsList) {
                if (temp.getId().equals(userId)) {
                    user = temp;
                }
            }
            if (user != null) {
                Glide.with(this).load(user.getPicture()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.constraintLayoutUserDetails.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(binding.imageViewDp);

                binding.textViewFullName.setText(user.getF_name() + " " + user.getL_name());
                binding.textViewTime.setText(stories.get(pos).getCreated_at());
            }

            //Story in GROUP
            if(!(stories.get(pos).getGroupstory()).contains("firebasestorage.googleapis.com")){

                      switchPlayer(1);
                      pauseStory();
                      binding.progressBarLoad.setVisibility(View.VISIBLE);
                      Glide.with(StoryViewerActivity.this).load(URL + stories.get(pos).getGroupstory()).listener(new RequestListener<Drawable>() {
                          @Override
                          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                              binding.progressBarLoad.setVisibility(View.GONE);
                              resumeStory();
                              return false;

                          }
                          @Override
                          public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                              binding.progressBarLoad.setVisibility(View.GONE);
                              resumeStory();
                              return false;
                          }
                      }).into(image);
                  }
            else{

                switchPlayer(2);
                binding.progressBarLoad.setVisibility(View.VISIBLE);
                pauseStory();
                binding.videoViewStory.setVideoPath(cachingUrl(stories.get(pos).getGroupstory()));
                binding.videoViewStory.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        binding.progressBarLoad.setVisibility(View.GONE);
                        resumeStory();
                    }
                });
                binding.videoViewStory.start();
                binding.videoViewStory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        storiesProgressView.skip();
                    }
                });


            }


        }//End of Group Section

        //User Details in STATUS
        if (intentType.equals(STORY_STATUS)) {

            Friends user = null;
            for (Friends temp : friendsList) {
                if (temp.getId().equals(statusId)) {
                    user = temp;
                }
            }
            if (user != null) {


                Glide.with(this).load(user.getPicture()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.constraintLayoutUserDetails.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(binding.imageViewDp);

                binding.textViewFullName.setText(user.getF_name() + " " + user.getL_name());
                binding.textViewTime.setText(user.getUsername());
                binding.constraintLayoutUserDetails.setVisibility(View.VISIBLE);
            }

            if(!(statuses.get(pos)).contains("firebasestorage.googleapis.com")) {
                switchPlayer(1);
                pauseStory();
                binding.progressBarLoad.setVisibility(View.VISIBLE);

                Glide.with(this).load(URL + statuses.get(pos)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.progressBarLoad.setVisibility(View.GONE);
                        Toast.makeText(StoryViewerActivity.this, "This Story is not available anymore", Toast.LENGTH_SHORT).show();
                            resumeStory();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.progressBarLoad.setVisibility(View.GONE);
                        resumeStory();
                        return false;
                    }
                }).into(image);


            }else{
                switchPlayer(2);
                binding.progressBarLoad.setVisibility(View.VISIBLE);
                    pauseStory();
                binding.videoViewStory.setVideoPath(cachingUrl(statuses.get(pos)));

                binding.videoViewStory.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        binding.progressBarLoad.setVisibility(View.GONE);
                        resumeStory();
                    }
                });
                binding.videoViewStory.start();
                binding.videoViewStory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        storiesProgressView.skip();
                    }
                });
            }

        }
        //User Details in COMPANY
        if (intentType.equals(STORY_COMPANY)) {
            String userId = companyStories.get(pos).getUserid();
            Friends user = null;
            for (Friends temp : friendsList) {
                if (temp.getId().equals(userId)) {
                    user = temp;
                }
            }
            if (user != null) {
                Glide.with(this).load(user.getPicture()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.constraintLayoutUserDetails.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(binding.imageViewDp);
                binding.textViewFullName.setText(user.getF_name() + " " + user.getL_name());
                binding.textViewTime.setText(companyStories.get(pos).getCreated_at());
            }
                if(!companyStories.get(pos).getStorypicture().contains("firebasestorage.googleapis.com")){
                    switchPlayer(1);
                    pauseStory();
                    binding.progressBarLoad.setVisibility(View.VISIBLE);
                    Glide.with(StoryViewerActivity.this).load(URL + companyStories.get(pos).getStorypicture()).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.progressBarLoad.setVisibility(View.GONE);
                            Toast.makeText(StoryViewerActivity.this, "This Story is not available anymore", Toast.LENGTH_SHORT).show();
                            resumeStory();
                            return false;

                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.progressBarLoad.setVisibility(View.GONE);
                            resumeStory();
                            return false;
                        }
                    }).into(image);
                }else{
                    switchPlayer(2);
                    pauseStory();
                    binding.progressBarLoad.setVisibility(View.VISIBLE);
                    binding.videoViewStory.setVideoPath(cachingUrl(companyStories.get(pos).getStorypicture()));

                    binding.videoViewStory.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {

                            binding.progressBarLoad.setVisibility(View.GONE);
                            resumeStory();
                        }
                    });
                    binding.videoViewStory.start();
                    binding.videoViewStory.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            storiesProgressView.skip();
                        }
                    });
                }



        }

    }

    @Override
    public void response(Map<String, String> response) {

    }

    @Override
    public void errorRequest(Map<String, String> response) {

    }

    @Override
    public void responseList(List<Friends> friendsList) {
        binding.progressBarLoad.setVisibility(View.GONE);
        if (friendsList != null) {
            this.friendsList = friendsList;
            intentType = getIntent().getStringExtra("type");

            if (intentType.equals(STORY_STATUS)) {
                statuses = getIntent().getStringArrayListExtra("stories");
                statusId = getIntent().getStringExtra("statusId");
                storiesProgressView.setStoriesCount(statuses.size());
                storiesProgressView.setStoryDuration(5000L);
                storiesProgressView.setStoriesListener(this);
                storiesProgressView.startStories();

            }
            if (intentType.equals(STORY_GROUPS)) {
                Type typeGroup_Stories = new TypeToken<List<Feed.GroupsBean.GrouppicturesBean>>() {
                }.getType();
                stories = gson.fromJson(getIntent().getStringExtra("stories"), typeGroup_Stories);
                Log.d("afakjf", "getUsersData: Restcall story Group");

                storiesProgressView.setStoriesCount(stories.size());
                storiesProgressView.setStoryDuration(5000L);
                storiesProgressView.setStoriesListener(this);
                storiesProgressView.startStories();

            }
            if (intentType.equals(STORY_COMPANY)) {
                Type typeGroup_Stories = new TypeToken<List<Feed.CompanyBean.StorypictureBean>>() {
                }.getType();
                companyStories = gson.fromJson(getIntent().getStringExtra("stories"), typeGroup_Stories);
                storiesProgressView.setStoriesCount(companyStories.size());
                storiesProgressView.setStoryDuration(5000L);
                storiesProgressView.setStoriesListener(this);
                storiesProgressView.startStories();
            }
            setImage(counter);
        } else {
            Toast.makeText(this, "Problem Loading Sender's Data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void responseListGroups(List<Groups> groupsList) {

    }

    @Override
    public void responseListCompany(List<Company> companyList) {

    }

    private void switchPlayer(int player){
          //1 - Image
        //2 - Video
        if(player == 1){
             binding.imageViewStory.setVisibility(View.VISIBLE);
            binding.videoViewStory.setVisibility(View.GONE);
        }else{
            binding.imageViewStory.setVisibility(View.GONE);
            binding.videoViewStory.setVisibility(View.VISIBLE);
        }

    }

    //VideoCache
    private  String cachingUrl(String urlPath) {
        return Application.getProxy(this).getProxyUrl(urlPath, true);
    }

    private void pauseStory(){

        Handler mainHandler = new Handler(StoryViewerActivity.this.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                storiesProgressView.pause();
            }
        };
        mainHandler.post(myRunnable);

    }

    private void resumeStory(){
        Handler mainHandler = new Handler(StoryViewerActivity.this.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                storiesProgressView.resume();
            }
        };
        mainHandler.post(myRunnable);
    }


}
