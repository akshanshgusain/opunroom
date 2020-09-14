package com.factor8.opUndoor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.hololo.tutorial.library.Step;
import static com.factor8.opUndoor.ProjectConstants.PREF_KEY_IS_FIRST_RUN;

public class ORTutorialActivity extends com.hololo.tutorial.library.TutorialActivity {
    private SharedPreferences tutorialPref;
    private SharedPreferences.Editor tutorialEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIndicatorSelected(R.drawable.ic_circular_indicator_selected);
        tutorialPref = getApplicationContext().getSharedPreferences("TutorialPreference", MODE_PRIVATE);

        addFragment(new Step.Builder().setTitle("Navigation")
                .setContent("Swipe Left to go to the Chats and view StoryBoards. Swipe Right to  go to the Camera")
                .setView(R.layout.activity_ortutorial)
                .setBackgroundColor(getResources().getColor(R.color.colorBlack)) // int background color
                .setDrawable(R.mipmap.custom_1) // int top drawable
                .setSummary("Chats coming soon!")
                .build());

        addFragment(new Step.Builder().setTitle("Take a Picture or Pick a Picture")
                .setContent("Take picture or hold the shutter button for video. \n Share a picture from your gallery directly.")
                .setView(R.layout.activity_ortutorial)
                .setBackgroundColor(getResources().getColor(R.color.colorBlack)) // int background color
                .setDrawable(R.mipmap.custom_2) // int top drawable
                .setSummary("To flip the camera or turn on/off Flash check top right corner")
                .build());


        addFragment(new Step.Builder().setTitle("Post Picture to StoryBoards")
                .setContent("Choose time period for story expiry & auto removal. \n Post stories to your Profile, Teams and Company.")
                .setView(R.layout.activity_ortutorial)
                .setBackgroundColor(getResources().getColor(R.color.colorBlack)) // int background color
                .setDrawable(R.mipmap.custom_4) // int top drawable
                .setSummary("")
                .build());

//        addFragment(new Step.Builder().setTitle("Your Storyboard")
//                .setContent("Storyboard allows you to access your Profile and StoryBoards. StoryBoards are divided into three types: Colleagues, Teams and Subscription")
//                .setView(R.layout.activity_ortutorial)
//                .setBackgroundColor(getResources().getColor(R.color.colorBlack)) // int background color
//                .setDrawable(R.mipmap.custom_6) // int top drawable
//                .setSummary("")
//                .build());

        addFragment(new Step.Builder().setTitle("Storyboards")
                .setContent("View Storyboards across Colleagues, Teams or Companies you have subscribed to.")
                .setView(R.layout.activity_ortutorial)
                .setBackgroundColor(getResources().getColor(R.color.colorBlack)) // int background color
                .setDrawable(R.mipmap.custom_8) // int top drawable
                .setSummary("Post a story and let your friends know.")
                .build());

//        addFragment(new Step.Builder().setTitle("Teams")
//                .setContent("StoryBoards of all the teams you are a part of. Click on the StoryBoard to view " +
//                        "a stories posted by your team or click on the add button to create your own team")
//                .setView(R.layout.activity_ortutorial)
//                .setBackgroundColor(getResources().getColor(R.color.colorBlack)) // int background color
//                .setDrawable(R.mipmap.custom_8) // int top drawable
//                .setSummary("Groups are Private and are only visible to the members of the group")
//                .build());
//
//        addFragment(new Step.Builder().setTitle("Subscriptions")
//                .setContent("All the StoryBoards you have subscribed to")
//                .setView(R.layout.activity_ortutorial)
//                .setBackgroundColor(getResources().getColor(R.color.colorBlack)) // int background color
//                .setDrawable(R.mipmap.custom_9) // int top drawable
//                .build());

//        addFragment(new Step.Builder().setTitle("All Done!")
//                .setContent("Start Sending Stories NOW!")
//                .setView(R.layout.activity_ortutorial)
//                .setBackgroundColor(getResources().getColor(R.color.colorBlack)) // int background color
//                .setDrawable(R.mipmap.ic_launcher_round) // int top drawable
//                .build());


    }



    @Override
    public void currentFragmentPosition(int position) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void finishTutorial() {
        super.finishTutorial();
        tutorialEditor = tutorialPref.edit();
        tutorialEditor.putBoolean(PREF_KEY_IS_FIRST_RUN, false);
        tutorialEditor.apply();

        Intent p = new Intent(this, MainActivity.class);
        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(p);
        finish();
    }

}