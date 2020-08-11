package com.factor8.opUndoor.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.factor8.opUndoor.MainActivity;
import com.factor8.opUndoor.ProjectConstants;
import com.bumptech.glide.Glide;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.ActivityProfileMainBinding;

import static com.factor8.opUndoor.ProjectConstants.COMPANY_IMAGES;
import static com.factor8.opUndoor.ProjectConstants.PROFILE_IMAGES;


public class ProfileMainActivity extends AppCompatActivity {
    private ActivityProfileMainBinding binding;
    SharedPreferences pref;
    private  SharedPreferences.Editor editor;
    private static final String TAG = "ProfileMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_main);
        pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);

        Log.i("TESTING", "CREATED: " + getClass().getSimpleName() + " -- TASK ID: " + getTaskId());
        binding.imageViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileMainActivity.this, SettingsActivity.class));
            }
        });

        binding.imageViewDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileMainActivity.this, SettingsActivity.class));
            }
        });
        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

      binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             // Toast.makeText(ProfileMainActivity.this, "Logout Call", Toast.LENGTH_SHORT).show();
              editor = pref.edit();
              editor.clear();
              editor.apply();
              finish();
          }
      });

      loadDataFromPreference();
    }

    private void loadDataFromPreference() {
        Glide.with(this)
                .load(COMPANY_IMAGES+pref.getString(ProjectConstants.PREF_KEY_COMPANY_PICTURE, ""))
                .into(binding.imageViewCompanyDp);
        Glide.with(this)
                .load(COMPANY_IMAGES+pref.getString(ProjectConstants.PREF_KEY_COMPANY_COVER, ""))
                .into(binding.imageViewBannerImage);
        binding.textViewUsername.setText(pref.getString(ProjectConstants.PREF_KEY_USERNAME, ""));
        binding.textViewComapnyFullName.setText(pref.getString(ProjectConstants.PREF_KEY_NETWORK, ""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TESTING", "DESTROYED: " + getClass().getSimpleName() + " -- TASK ID: " + getTaskId());
    }

    @Override
    protected void onResume() {
        super.onResume();


        Glide.with(this)
                .load(PROFILE_IMAGES+pref.getString(ProjectConstants.PREF_KEY_PICTURE, ""))
                .into(binding.imageViewDp);
        Glide.with(this)
                .load(PROFILE_IMAGES+pref.getString(ProjectConstants.PREF_KEY_COVER, ""))
                .into(binding.imageViewCover);

        String fullName = pref.getString(ProjectConstants.PREF_KEY_F_NAME, "") + " " + pref.getString(ProjectConstants.PREF_KEY_L_NAME, "");
        binding.textViewName.setText(fullName);

        binding.textViewProfession.setText(pref.getString(ProjectConstants.PREF_KEY_PROFESSION, ""));

        String yearsOfExperience = pref.getString(ProjectConstants.PREF_KEY_EXPERIENCE, "")+"of experience";
        binding.textViewExperience.setText(yearsOfExperience);



    }


}
