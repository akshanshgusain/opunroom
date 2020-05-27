package org.akshanshgusain.killmonger2test.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import org.akshanshgusain.killmonger2test.MainActivity;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityProfileMainBinding;

import static org.akshanshgusain.killmonger2test.ProjectConstants.*;

public class ProfileMainActivity extends AppCompatActivity {
    private ActivityProfileMainBinding binding;
    SharedPreferences pref;
    private  SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_main);
        pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
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
      binding.constraintLayoutAddToStory.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
               Intent o = new Intent(ProfileMainActivity.this, MainActivity.class);
               o.putExtra("frag" ,1);
               o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(o);
               finish();
          }
      });
      binding.constraintLayoutAddToGroup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent o = new Intent(ProfileMainActivity.this, MainActivity.class);
              o.putExtra("frag" ,1);
              o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
              o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(o);
              finish();
          }
      });
      binding.constraintLayoutLogout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              editor = pref.edit();
              editor.clear();
              editor.apply();
              Intent i = new Intent(ProfileMainActivity.this, MainActivity.class);
              i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(i);
              finish();
          }
      });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).load(pref.getString(PREF_KEY_PICTURE, "")).into(binding.imageViewDp);
        binding.textViewName.setText(pref.getString(PREF_KEY_F_NAME, "") + " " + pref.getString(PREF_KEY_L_NAME, ""));
        binding.textViewUsername.setText(pref.getString(PREF_KEY_USERNAME, ""));
    }
}
