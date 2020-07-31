package com.factor8.opUndoor.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.factor8.opUndoor.ProjectConstants;
import com.factor8.opUndoor.Network.RestCalls;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.ActivityTransitionBinding;

import java.util.Map;

public class TransitionActivity extends AppCompatActivity implements RestCalls.RegisterUserI {
  Bundle bundle;
  ActivityTransitionBinding binding;
    private static final String TAG = "TransitionA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transition);
        bundle = getIntent().getBundleExtra(ProjectConstants.BUNDLE_REGISTER);
       binding.progressBarUpdate.setVisibility(View.VISIBLE);

       binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


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

            //Prrsenet ALert
            AlertDialog.Builder alert = new AlertDialog.Builder(TransitionActivity.this)
                    .setTitle("Email Verification")
                    .setCancelable(false)
                    .setMessage("A verification link has been sent to your Email Address. Verify and Login again.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent y = new Intent(TransitionActivity.this, LoginActivity.class);
                            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(y);
                            finish();
                        }
                    });
            alert.show();

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
