package org.akshanshgusain.killmonger2test.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityEnterNetworkBinding;

import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.BUNDLE_REGISTER;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_NAME_WORKSPACE;

public class EnterNetworkActivity extends AppCompatActivity implements RestCalls.CheckNetworkCallI {
     private ActivityEnterNetworkBinding binding;
    private static final String TAG = "EnterNetworkTRAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_network);
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.editTextWorkspace.getText().toString().isEmpty()){
                    checkForValidNetwork();
                }
                else{
                    binding.editTextWorkspace.setText("This Field can not be empty");
                }
            }
        });
    }

    private void checkForValidNetwork() {
        RestCalls restCall = new RestCalls(this);
        Log.d(TAG, "checkForValidNetwork: "+binding.editTextWorkspace.getText().toString().trim()+".com" );
        restCall.checkNetworkCall( binding.editTextWorkspace.getText().toString().trim());
    }

    @Override
    public void response(Map<String, String> response) {
        Log.d(TAG, "response: "+ response);
              if(response.get("status") .equals("1")){
                  //Network Available :
                  //Call Service and then call next Activity
                  Intent i = new Intent(EnterNetworkActivity.this, EnterDetailsActivity.class);
                  Bundle bundle = new Bundle();
                  bundle.putString(INTENT_NAME_WORKSPACE,binding.editTextWorkspace.getText().toString() );
                  i.putExtra(BUNDLE_REGISTER, bundle );
                  startActivity(i);
              }
              if(response.get("status") .equals("0")) {
                  binding.editTextWorkspace.setText("Network Does not Exist");
              }
    }

    @Override
    public void errorRequest(Map<String, String> response) {
        Log.d(TAG, "Volley Error: "+ response.get("VolleyError"));
        Log.d(TAG, "Exception : "+ response.get("exception"));
    }
}
