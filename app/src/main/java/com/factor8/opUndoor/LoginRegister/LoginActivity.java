package com.factor8.opUndoor.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.factor8.opUndoor.MainActivity;
import com.factor8.opUndoor.Network.RestCalls;
import com.factor8.opUndoor.ORTutorialActivity;
import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.Utils;
import com.factor8.opUndoor.databinding.ActivityLoginBinding;


import java.util.Map;

public class LoginActivity extends AppCompatActivity implements RestCalls.LoginUserI {
    private ActivityLoginBinding binding;
    private static final String TAG = "LoginAct";
    private SharedPreferences loginPref;
    private SharedPreferences.Editor loginEditor;

    private SharedPreferences tutorialPref;
    private SharedPreferences.Editor tutorialEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginPref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        tutorialPref = getApplicationContext().getSharedPreferences("TutorialPreference", MODE_PRIVATE);
        Log.i("TESTING", "CREATED: " + getClass().getSimpleName() + " -- TASK ID: " + getTaskId());

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(LoginActivity.this);
                 startActivity(new Intent(LoginActivity.this, EnterNetworkActivity.class));
            }
        });
        binding.buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(LoginActivity.this);
                 signIn();
            }
        });
        binding.textViewForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
            }
        });
    }

    private void signIn() {
         if(binding.editTextUsername.getText().toString().isEmpty()){
             binding.textViewDetails.setVisibility(View.VISIBLE);
             binding.textViewDetails.setText("Username or Email cannot be empty");
         }
        else if(binding.editTextPassword.getText().toString().isEmpty()){
             binding.textViewDetails.setVisibility(View.VISIBLE);
             binding.textViewDetails.setText("Password cannot be empty");
         }
         else{
             //make Login Rest Call
             RestCalls restCalls = new RestCalls(this);
             restCalls.loginCall(binding.editTextUsername.getText().toString().trim(), binding.editTextPassword.getText().toString().trim());
             binding.progressBarUpdate.setVisibility(View.VISIBLE);
         }

    }

    @Override
    public void response(Map<String, String> response) {
        binding.progressBarUpdate.setVisibility(View.GONE);
        if (!response.get("id").equals("")) {
                if(response.get("is_verified").equals("0")){
                    android.app.AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Email Not Verified")
                            .setCancelable(false)
                            .setMessage("A verification email has been sent to your registered email address. Please verify your email and try again.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    alert.show();
                }else{
                    Log.d(TAG, "response: success");
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    //Create Preference Here:
                    loginEditor = loginPref.edit();


                    loginEditor.putString(ProjectConstants.PREF_KEY_ID, response.get("id"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_F_NAME, response.get("f_name"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_L_NAME, response.get("l_name"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_USERNAME, response.get("username"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_EMAIL, response.get("email"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_NETWORK, response.get("network"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_PICTURE, response.get("picture"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_PROFESSION, response.get("profession"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_EXPERIENCE, response.get("experience"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_IS_PRIVATE, response.get("privacy"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_COVER, response.get("coverpic"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_COMPANY_COVER, response.get("networkcover"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_COMPANY_PICTURE, response.get("networkprofile"));
                    loginEditor.putString(ProjectConstants.PREF_KEY_CURRENT_COMPANY, response.get("current_company"));
                    loginEditor.putBoolean(ProjectConstants.PREF_KEY_IS_LOGIN, true);


                    loginEditor.apply();


                    if(tutorialPref.getBoolean(ProjectConstants.PREF_KEY_IS_FIRST_RUN, true)){
                        Intent p = new Intent(LoginActivity.this, ORTutorialActivity.class);
                        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(p);
                        finish();

                    }else{
                        Intent p = new Intent(LoginActivity.this, MainActivity.class);
                        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(p);
                        finish();
                    }
                }
        }
        if (response.get("id").equals("")) {
            //Toast.makeText(this, " response Failed"+ response.get("id"), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "response: Error");
            Log.d(TAG, "response: Error" + response.get("message"));
            binding.textViewDetails.setVisibility(View.VISIBLE);
            binding.textViewDetails.setText("Invalid username or password");

            android.app.AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Invalid Credentials")
                    .setCancelable(false)
                    .setMessage("Invalid username or password")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                        }
                    });
            alert.show();
        }
    }

    @Override
    public void errorRequest(Map<String, String> response) {
        binding.progressBarUpdate.setVisibility(View.GONE);
        Log.d(TAG, "ErrorRequest : Volley Error "+ response.get("VolleyError"));
        Toast.makeText(this, "ErrorRequest : Volley Error"+ response.get("VolleyError"), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "ErrorRequest (Exception): "+ response.get("exception"));
        Toast.makeText(this, "(Exception): "+ response.get("exception"), Toast.LENGTH_SHORT).show();
        binding.textViewDetails.setText(response.get("message"));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TESTING", "DESTROYED: " + getClass().getSimpleName() + " -- TASK ID: " + getTaskId());
    }
}
