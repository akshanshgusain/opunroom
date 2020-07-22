package org.akshanshgusain.killmonger2test.LoginRegister;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityForgetPassBinding;

import java.util.Map;

public class ForgetPassActivity extends AppCompatActivity implements RestCalls.ForgetPasswordI {
    ActivityForgetPassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget_pass );
        binding.buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.editTextReset.getText().toString().isEmpty()){
                    binding.editTextReset.setHint("Email address can not be empty");
                }else{
                    //call service
                    RestCalls restCalls = new RestCalls(ForgetPassActivity.this);
                    restCalls.forgetPassword(binding.editTextReset.getText().toString().trim());
                }
            }
        });

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void responseForgetPass(Map<String, String> response) {
         if(response.get("status").equals("1")){
//             AlertDialog.Builder alert = new AlertDialog.Builder(this)
//                     .setTitle("Password Rest")
//                     .setMessage("A password reset link has been sent to your email address")
//                     .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
//                         @Override
//                         public void onClick(DialogInterface dialogInterface, int i) {
//                               dialogInterface.dismiss();
//                              ForgetPassActivity.this.finish();
//                         }
//                     }).setCancelable(false);
//             alert.show();



         }else if(response.get("status").equals("0")){
             AlertDialog.Builder alert = new AlertDialog.Builder(this)
                     .setTitle("Invalid Email")
                     .setMessage("The Email you entered is not a valid Email")
                     .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.dismiss();
                         }
                     }).setCancelable(false);
             alert.show();
         }else{
             AlertDialog.Builder alert = new AlertDialog.Builder(this)
                     .setTitle("Something Went Wrong")
                     .setMessage("Please try again")
                     .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.dismiss();
                         }
                     }).setCancelable(false);
             alert.show();
         }
    }

    @Override
    public void errorForgetPass(Map<String, String> error) {

    }
}
