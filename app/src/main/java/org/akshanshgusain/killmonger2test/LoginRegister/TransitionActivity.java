package org.akshanshgusain.killmonger2test.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityTransitionBinding;

import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.*;

public class TransitionActivity extends AppCompatActivity implements RestCalls.RegisterUserI {
  Bundle bundle;
  ActivityTransitionBinding binding;
    private static final String TAG = "TransitionA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transition);
        bundle = getIntent().getBundleExtra(BUNDLE_REGISTER);
       binding.progressBarUpdate.setVisibility(View.VISIBLE);

       binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(TransitionActivity.this, LoginActivity.class);
               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(i);
               finish();
           }
       });
       RestCalls restCalls = new RestCalls(this);
       restCalls.registerUser(bundle);
    }

    @Override
    public void response(Map<String, String> response)  {
        binding.progressBarUpdate.setVisibility(View.GONE);
        if (response.get("status").equals("1")) {
            Log.d(TAG, "response: success");
            binding.buttonLogin.setVisibility(View.VISIBLE);
            binding.h2.setText("All Done, Please Login with with your username");
        }
        if (response.get("status").equals("0")) {
            Log.d(TAG, "response: Error");
            Log.d(TAG, "response: Error" + response.get("message"));
            binding.textViewError1.setText("Email Already Registered");
            binding.textViewDetails.setVisibility(View.VISIBLE);

            binding.textViewError2.setText(response.get("message"));
            binding.textViewDetails2.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void errorRequest(Map<String, String> response) {
        binding.progressBarUpdate.setVisibility(View.GONE);
        Log.d(TAG, "ErrorRequest : "+ response.get("VolleyError"));
        Log.d(TAG, "ErrorRequest (Exception): "+ response.get("exception"));
        binding.textViewError1.setText("Email Already Registered");
        binding.textViewDetails.setVisibility(View.VISIBLE);
    }
}
