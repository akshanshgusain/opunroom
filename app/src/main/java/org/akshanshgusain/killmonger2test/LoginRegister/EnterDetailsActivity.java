package org.akshanshgusain.killmonger2test.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityEnterDetailsBinding;

import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.BUNDLE_REGISTER;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_EMAIL_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PASSWORD_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_USERNAME_WORKSPACE;

public class EnterDetailsActivity extends AppCompatActivity implements RestCalls.CheckUsernameI {
    ActivityEnterDetailsBinding binding;
    boolean usernameAvailable = false, passwordMatches = false;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        bundle = getIntent().getBundleExtra(BUNDLE_REGISTER);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_details);
        binding.textViewWorkspaceEmail.setText("@"+bundle.get(INTENT_NAME_WORKSPACE).toString().trim()+".com");

        binding.editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //Call service to check whether or no the entered username is available
                if (!b) {
                    RestCalls restCall = new RestCalls(EnterDetailsActivity.this);
                    restCall.checkUserName(binding.editTextUsername.getText().toString().trim());
                } else {
                    binding.textViewUsernameAvailable.setVisibility(View.GONE);
                }
            }
        });

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
    }

    private void validateFields() {
        if (binding.editTextEmail.getText().toString().isEmpty()) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Email cannot be Empty");
        }
      else  if (binding.editTextUsername.getText().toString().isEmpty()) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Username cannot be Empty");
        }
       else if (binding.editTextPassword.getText().toString().isEmpty()) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Password cannot be Empty");
        }
       else if (binding.editTextPasswordRe.getText().toString().isEmpty()) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Re-Enter Password");
        }
       else if (binding.editTextPassword.length() < 0) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Password cannot be Empty");
        }
      else  if (
                !binding.editTextPassword.getText().toString().equals(binding.editTextPasswordRe.getText().toString())) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Password does not match");
        }
      else{
            Toast.makeText(this, "Works Fine", Toast.LENGTH_SHORT).show();
            binding.textViewPasswordErrors.setVisibility(View.GONE);
            binding.textViewUsernameAvailable.setVisibility(View.GONE);

            bundle.putString(INTENT_EMAIL_WORKSPACE, binding.editTextEmail.getText().toString().trim()+"@"+bundle.get(INTENT_NAME_WORKSPACE).toString().trim()+".com");
            bundle.putString(INTENT_USERNAME_WORKSPACE, binding.editTextUsername.getText().toString().trim());
            bundle.putString(INTENT_PASSWORD_WORKSPACE, binding.editTextPassword.getText().toString().trim());
            Intent i = new Intent(this, EnterDetails2Activity.class);
            i.putExtra(BUNDLE_REGISTER, bundle);
            startActivity(i);
        }
    }

    //---- Rest Call back methods
    @Override
    public void response(Map<String, String> response) {
        if (response.get("status").equals("1")) {
            binding.textViewUsernameAvailable.setVisibility(View.VISIBLE);
            binding.textViewUsernameAvailable.setText("Username Available");
        }
        if (response.get("status").equals("0")) {
            binding.textViewUsernameAvailable.setVisibility(View.VISIBLE);
            binding.textViewUsernameAvailable.setText("Username Not Available");
        }
    }

    @Override
    public void errorRequest(Map<String, String> response) {

    }
}
