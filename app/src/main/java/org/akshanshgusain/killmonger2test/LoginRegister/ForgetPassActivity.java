package org.akshanshgusain.killmonger2test.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityForgetPassBinding;

public class ForgetPassActivity extends AppCompatActivity {
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
                    finish();
                }
            }
        });
    }
}
