package org.akshanshgusain.killmonger2test.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.akshanshgusain.killmonger2test.MainActivity;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.SplashActivity;
import org.akshanshgusain.killmonger2test.databinding.ActivityLoginBinding;

import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.*;

public class LoginActivity extends AppCompatActivity implements RestCalls.LoginUserI {
    private ActivityLoginBinding binding;
    private static final String TAG = "LoginAct";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
   public static TextView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
      mHeader = binding.imageViewex;
        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(LoginActivity.this, EnterNetworkActivity.class));
            }
        });
        binding.buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            Log.d(TAG, "response: success");
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            //Create Preference Here:
            editor = pref.edit();
            editor.putString(PREF_KEY_ID, response.get("id"));
            editor.putString(PREF_KEY_F_NAME, response.get("f_name"));
            editor.putString(PREF_KEY_L_NAME, response.get("l_name"));
            editor.putString(PREF_KEY_USERNAME, response.get("username"));
            editor.putString(PREF_KEY_EMAIL, response.get("email"));
            editor.putString(PREF_KEY_NETWORK, response.get("network"));
            editor.putString(PREF_KEY_PICTURE, response.get("picture"));
            editor.putBoolean(PREF_KEY_IS_LOGIN, true);
            editor.apply();
            Intent p = new Intent(LoginActivity.this, MainActivity.class);
            p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(p);
            finish();
        }
        if (response.get("id").equals("")) {
            Toast.makeText(this, " response Failed"+ response.get("id"), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "response: Error");
            Log.d(TAG, "response: Error" + response.get("message"));
            binding.textViewDetails.setText(response.get("message"));
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
}
