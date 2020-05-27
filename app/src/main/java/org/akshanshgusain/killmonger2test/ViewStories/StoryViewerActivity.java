package org.akshanshgusain.killmonger2test.ViewStories;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityStoryViewerBinding;

import java.util.ArrayList;

public class StoryViewerActivity extends AppCompatActivity {
   private ActivityStoryViewerBinding binding;
   ArrayList<String> stories;
    private static final String TAG = "ViewerStory";
    public static final String URL="http://dass.io/oppo/app/story/image/";
    int mCurrentPicture=0;
    int mNumberOfImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_story_viewer);
        stories = getIntent().getStringArrayListExtra("stories");
        mNumberOfImages = stories.size();
        for(String story : stories){
            Log.d(TAG, "onCreate: "+ story);
        }
              if(stories!=null){
                  setImage(0);
              }
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                       if(mCurrentPicture<mNumberOfImages-1){
                            mCurrentPicture++;
                            setImage(mCurrentPicture);
                       }
            }
        });
        binding.buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(! (mCurrentPicture==0)){
                    mCurrentPicture--;
                    setImage(mCurrentPicture);
                }
            }
        });
    }

    void setImage(int pos){
        binding.progressBarLoad.setVisibility(View.VISIBLE);
        Log.d(TAG, "setImage: image: "+ URL+stories.get(pos));
        Glide.with(this).load(URL+stories.get(pos)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                binding.progressBarLoad.setVisibility(View.GONE);
                Toast.makeText(StoryViewerActivity.this, "This Story is not available anymore", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                binding.progressBarLoad.setVisibility(View.GONE);
                return false;
            }
        }).into(binding.imageViewStory);
    }
}
