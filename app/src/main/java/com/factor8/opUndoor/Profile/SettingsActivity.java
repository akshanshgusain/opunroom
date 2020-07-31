package com.factor8.opUndoor.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.factor8.opUndoor.ProjectConstants;
import com.bumptech.glide.Glide;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.ActivitySettingsBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.factor8.opUndoor.Network.RestCalls;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements RestCalls.UpdateUserProfileI {
    private ActivitySettingsBinding binding;
    Bitmap imageBitMap = null;
    private static final String TAG = "SettingsAct";
    SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        binding.editTextFullName.setText(pref.getString(ProjectConstants.PREF_KEY_F_NAME, "NameDefault"));
        binding.editTextEmail.setText(pref.getString(ProjectConstants.PREF_KEY_L_NAME, "lastNameDefault"));
        Glide.with(this).load(pref.getString(ProjectConstants.PREF_KEY_PICTURE, "NameDefault")).into(binding.imageViewUserDisplayPicture);

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        binding.imageViewUserDisplayPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageCropper();
            }
        });
        getCurrentData();
    }

    private void getCurrentData() {
        bundle.putString("f_name", pref.getString(ProjectConstants.PREF_KEY_F_NAME, "1"));
        bundle.putString("l_name", pref.getString(ProjectConstants.PREF_KEY_L_NAME, "1"));
        bundle.putString("id", pref.getString(ProjectConstants.PREF_KEY_ID, "1"));
        bundle.putString("username", pref.getString(ProjectConstants.PREF_KEY_USERNAME, "1"));
        bundle.putString("email", pref.getString(ProjectConstants.PREF_KEY_EMAIL, "@"));
        bundle.putString("network", pref.getString(ProjectConstants.PREF_KEY_NETWORK, "1"));
    }

    private void update() {
        binding.progressBarUpdate.setVisibility(View.VISIBLE);
        bundle.putString("f_name", binding.editTextFullName.getText().toString().trim());
        bundle.putString("l_name", binding.editTextEmail.getText().toString().trim());
        bundle.putByteArray("picture", getFileDataFromDrawable(imageBitMap));

        RestCalls restCalls = new RestCalls(this);
        restCalls.updateUserProfile(bundle);

    }

    private void ImageCropper() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    imageBitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    binding.imageViewUserDisplayPicture.setImageURI(resultUri);
                } catch (IOException e) {
                    Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            bitmap = ((BitmapDrawable) binding.imageViewUserDisplayPicture.getDrawable()).getBitmap();

        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        Log.d(TAG, "getFileDataFromDrawable: image: " + byteArrayOutputStream.toByteArray());
        return byteArrayOutputStream.toByteArray();

    }


    @Override
    public void response(Map<String, String> response) {
        binding.progressBarUpdate.setVisibility(View.GONE);
        if (!response.get("id").equals("")) {
            Log.d(TAG, "response: success");

            //ChangePreference .
            editor = pref.edit();
            editor.putString(ProjectConstants.PREF_KEY_F_NAME, response.get("f_name"));
            editor.putString(ProjectConstants.PREF_KEY_L_NAME, response.get("l_name"));
            editor.putString(ProjectConstants.PREF_KEY_USERNAME, response.get("username"));
            editor.putString(ProjectConstants.PREF_KEY_EMAIL, response.get("email"));
            editor.putString(ProjectConstants.PREF_KEY_NETWORK, response.get("network"));
            editor.putString(ProjectConstants.PREF_KEY_PICTURE, response.get("picture"));
            editor.apply();



            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (response.get("id").equals("")) {
            Log.d(TAG, "response: Error");
            Log.d(TAG, "response: Error" + response.get("message"));
            Toast.makeText(this, "Please try again after some time", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void errorRequest(Map<String, String> response) {
        binding.progressBarUpdate.setVisibility(View.GONE);
        Log.d(TAG, "ErrorRequest : " + response.get("VolleyError"));
        Log.d(TAG, "ErrorRequest (Exception): " + response.get("exception"));
        Toast.makeText(this, "Please try again after some time", Toast.LENGTH_SHORT).show();
    }
}
