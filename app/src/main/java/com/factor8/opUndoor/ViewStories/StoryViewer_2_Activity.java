package com.factor8.opUndoor.ViewStories;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.factor8.opUndoor.Network.Responses.Feed;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.ActivityStoryViewer2Binding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class StoryViewer_2_Activity extends SwipeDismissBaseActivity implements StoriesProgressView.StoriesListener {


    private ActivityStoryViewer2Binding binding;

    String currentUrl ="opundoor.com";

    List<Feed.NewsBean.TimesofindiaBean> toiFeed = new ArrayList<>();
    List<Feed.NewsBean.EconomictimesBean> tetFeeds = new ArrayList<>();
    String type = "";

    private static final String TAG = "ViewerStory";
    public static final String URL = "http://dass.io/oppo/app/story/image/";

    private String DP_TOI = "https://dass.io/oppo/newscoverimage/download.jpeg";
    private String DP_ET = "https://dass.io/oppo/newscoverimage/ETDP.jpg";


    SharedPreferences pref;

    private  StoriesProgressView storiesProgressView;
    private ImageView image;

    private int counter = 0;


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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_story_viewer_2);
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

        binding.readMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentUrl));
                startActivity(browserIntent);
            }
        });


    }


    private void getUsersData() {

         this.type = getIntent().getStringExtra("type");

        Type typeCls = new TypeToken<Feed.NewsBean>() {
        }.getType();
        Feed.NewsBean news = gson.fromJson(getIntent().getStringExtra("stories"), typeCls);

        if(type.equals("TOI")){
            toiFeed = news.getTimesofindia();
            //binding.imageViewDp.setImageDrawable(getDrawable(R.drawable.ic_science));
            Glide.with(this).load(DP_TOI).into(binding.imageViewDp);
            storiesProgressView.setStoriesCount(toiFeed.size());
            binding.textViewFullName.setText("THE TIMES OF INDIA");
        }
       else if(type.equals("TET")){
            tetFeeds = news.getEconomictimes();
            //binding.imageViewDp.setImageDrawable(getDrawable(R.drawable.ic_business));
            Glide.with(this).load(DP_ET).into(binding.imageViewDp);
            storiesProgressView.setStoriesCount(tetFeeds.size());
            binding.textViewFullName.setText("THE ECONOMIC TIMES");
        }

        changeDrawableColor();

        storiesProgressView.setStoryDuration(10000L);
        storiesProgressView.setStoriesListener(this);
        storiesProgressView.startStories();
        setImage(counter);


    }


    @Override
    public void onNext() {
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

     binding.progressBarLoad.setVisibility(View.VISIBLE);

     if(type.equals("TOI")){
         String subSource = "Times Of India";
         currentUrl = toiFeed.get(pos).getGuid();
         binding.textViewSource.setText(subSource) ;

         // storiesProgressView.pause();

         Glide.with(this).asBitmap().load(toiFeed.get(pos).getImage()).listener(new RequestListener<Bitmap>() {
             @Override
             public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                 binding.progressBarLoad.setVisibility(View.GONE);
                 return false;
             }

             @Override
             public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                 binding.progressBarLoad.setVisibility(View.GONE);
                 return false;
             }
         }).into(binding.imageViewStory);


         binding.textViewHeading.setText(toiFeed.get(pos).getTitle());
         //binding.textViewContent.setText(toiFeed.get(pos).getDescription());
     }
        if(type.equals("TET")){
            String subSource = "Economic Times";
            currentUrl = tetFeeds.get(pos).getGuid();
            binding.textViewSource.setText(subSource) ;

            // storiesProgressView.pause();

            Glide.with(this).asBitmap().load(tetFeeds.get(pos).getImage()).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    binding.progressBarLoad.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    binding.progressBarLoad.setVisibility(View.GONE);
                    return false;
                }
            }).into(binding.imageViewStory);


            binding.textViewHeading.setText(tetFeeds.get(pos).getTitle());
            //binding.textViewContent.setText(toiFeed.get(pos).getDescription());
        }


    }

    private void changeDrawableColor(){
        int colorDrawable, colorText;

        if(type.equals("TOI")){
            colorDrawable = R.color.colorStoryBorderRed;
            colorText = R.color.colorWhite;

            binding.imaegViewRipple.setAnimation(R.raw.red_ripple);
        }else if(type.equals("TET")){
            colorDrawable = R.color.colorStoryBorderBlue;
            colorText = R.color.colorWhite;
            binding.imaegViewRipple.setAnimation(R.raw.blue_ripple);
        }else{
            colorDrawable = R.color.colorStoryBorderBlue;
            colorText = R.color.colorWhite;
            binding.imaegViewRipple.setAnimation(R.raw.blue_ripple);
        }

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_story_2_border);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(colorDrawable));
        binding.imageViewContentBG.setImageDrawable(getResources().getDrawable(R.drawable.ic_story_2_border));

        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(this, R.drawable.ic_story2_border2);
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getResources().getColor(colorDrawable));
        binding.imageViewTopBG.setImageDrawable(getResources().getDrawable(R.drawable.ic_story2_border2));

        Drawable unwrappedDrawable3 = AppCompatResources.getDrawable(this, R.drawable.ic_quotes);
        Drawable wrappedDrawable3 = DrawableCompat.wrap(unwrappedDrawable3);
        DrawableCompat.setTint(wrappedDrawable3, getResources().getColor(colorText));
        binding.imageViewQuotes.setImageDrawable(getResources().getDrawable(R.drawable.ic_quotes));

        binding.textViewHeading.setTextColor(getResources().getColor(colorText));
        binding.textViewSource.setTextColor(getResources().getColor(colorText));
        binding.textViewFullName.setTextColor(getResources().getColor(colorText));
        binding.textViewReadMore.setTextColor(getResources().getColor(colorText));
    }




}
