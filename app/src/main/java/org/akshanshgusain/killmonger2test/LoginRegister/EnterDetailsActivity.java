package org.akshanshgusain.killmonger2test.LoginRegister;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.Utils;
import org.akshanshgusain.killmonger2test.databinding.ActivityEnterDetailsBinding;

import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.BUNDLE_REGISTER;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_EMAIL_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PASSWORD_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_USERNAME_WORKSPACE;

public class EnterDetailsActivity extends AppCompatActivity implements RestCalls.CheckUsernameI, RestCalls.CheckEmailI {
    ActivityEnterDetailsBinding binding;
    boolean usernameAvailable = false, passwordMatches = false, emailAvailable = false;
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
                if (!b && !binding.editTextUsername.getText().toString().isEmpty()) {
                    checkForUserName();
                } else {
                    binding.textViewUsernameAvailable.setVisibility(View.GONE);
                }
            }
        });

        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideKeyboard(EnterDetailsActivity.this);
                validateFields();
            }
        });
    }

    private void validateFields() {
        if (binding.editTextEmail.getText().toString().isEmpty()) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Email cannot be Empty");
            Log.d("RestCall", "Email cannot be Empty");
        }
        else if (!(binding.editTextEmail.getText().toString().contains("@")
                && binding.editTextEmail.getText().toString().contains(".com"))) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Email is not valid");
            Log.d("RestCall", "Email is not valid");
        }
      else  if (binding.editTextUsername.getText().toString().isEmpty()) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Username cannot be Empty");
            Log.d("RestCall", "Username cannot be Empty");
        }
       else if (binding.editTextPassword.getText().toString().isEmpty()) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Password cannot be Empty");
            Log.d("RestCall", "Password cannot be Empty");
        }
       else if (binding.editTextPasswordRe.getText().toString().isEmpty()) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Re-Enter Password");
            Log.d("RestCall", "Re-Enter Password");
        }
       else if(!usernameAvailable){
            checkForUserName();
            binding.textViewUsernameAvailable.setText("Username Unvailable");
            Log.d("RestCall", "Username unavailable");
        }
       else if (binding.editTextPassword.length() < 0) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Password cannot be Empty");
            Log.d("RestCall", "Password cannot be Empty");
        }
        else if (binding.editTextPassword.length() < 6) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Password must be atleast 7 Character long");
            Log.d("RestCall", "Password must be atleast 7 Character long");
        }
      else  if (
                !binding.editTextPassword.getText().toString().equals(binding.editTextPasswordRe.getText().toString())) {
            binding.textViewPasswordErrors.setVisibility(View.VISIBLE);
            binding.textViewPasswordErrors.setText("Password does not match");
            Log.d("RestCall", "Password does not match");
        }
      else{
            Log.d("RestCall", "validateFields: Make Email check restCall");
            checkForUserName();
            RestCalls restCalls = new RestCalls(this);
            restCalls.checkEmail(binding.editTextEmail.getText().toString().trim());
            binding.progress.setVisibility(View.VISIBLE);
        }
    }

    private void checkForUserName(){
        RestCalls restCall = new RestCalls(EnterDetailsActivity.this);
        restCall.checkUserName(binding.editTextUsername.getText().toString().trim());
    }

    //---- Rest Call back methods
    @Override
    public void response(Map<String, String> response) {
        if(binding.editTextUsername.getText().toString().trim().length()>12){
            //Send Alert for username too long
            AlertDialog.Builder alert = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Invalid")
                    .setMessage("Username is too long. Choose a username less than 12 characters long.")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alert.show();
            binding.textViewUsernameAvailable.setVisibility(View.VISIBLE);
            binding.textViewUsernameAvailable.setText("Username Invalid");
            binding.textViewUsernameAvailable.setTextColor(getResources().getColor(R.color.colorNegative));
            usernameAvailable = false;
        }else{
            if (response.get("status").equals("1")) {
                binding.textViewUsernameAvailable.setVisibility(View.VISIBLE);
                binding.textViewUsernameAvailable.setText("Username Available");
                binding.textViewUsernameAvailable.setTextColor(getResources().getColor(R.color.colorPositive));
                usernameAvailable = true;
            }
            if (response.get("status").equals("0")) {
                binding.textViewUsernameAvailable.setVisibility(View.VISIBLE);
                binding.textViewUsernameAvailable.setText("Username Not Available");
                binding.textViewUsernameAvailable.setTextColor(getResources().getColor(R.color.colorNegative));
                usernameAvailable = false;
            }
        }
    }

    @Override
    public void errorRequest(Map<String, String> response) {

    }

    @Override
    public void responseEmailCheck(Map<String, String> response) {
        binding.progress.setVisibility(View.GONE);
        if(response.get("status").equals("1")){
            emailAvailable = true;
            if(emailAvailable){
                binding.textViewPasswordErrors.setVisibility(View.GONE);
                binding.textViewUsernameAvailable.setVisibility(View.GONE);

                bundle.putString(INTENT_EMAIL_WORKSPACE, binding.editTextEmail.getText().toString().trim());
                bundle.putString(INTENT_USERNAME_WORKSPACE, binding.editTextUsername.getText().toString().trim());
                bundle.putString(INTENT_PASSWORD_WORKSPACE, binding.editTextPassword.getText().toString().trim());
                Intent i = new Intent(this, EnterDetails2Activity.class);
                i.putExtra(BUNDLE_REGISTER, bundle);
                startActivity(i);
            }
        }else{
            emailAvailable = false;
            AlertDialog.Builder alert = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Email Already Exist")
                    .setMessage("An account with this email is already registered with us. If you do'nt " +
                            "remember the password try \"Forget password\"")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alert.show();
        }

    }

    @Override
    public void errorRequestEmailCheck(Map<String, String> response) {
        binding.progress.setVisibility(View.GONE);
        Toast.makeText(this, "Something went wrong. please Try again. ", Toast.LENGTH_SHORT).show();
    }
}
