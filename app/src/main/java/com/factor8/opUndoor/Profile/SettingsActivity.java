package com.factor8.opUndoor.Profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
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

import static com.factor8.opUndoor.ProjectConstants.PROFILE_IMAGES;

public class SettingsActivity extends AppCompatActivity implements RestCalls.UpdateUserProfileI {
    private ActivitySettingsBinding binding;
   // Bitmap imageBitMap = null;
    private static final String TAG = "SettingsAct";
    SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Bundle bundle = new Bundle();

    private String makePublicSwitch;
    private int pickPictureFlag = 0; // 1- Display Picture     2- Cover Picture

    private String[] profession_array = new String[20];
    private String currentProfession = "";
    private String[] exp_array = new String[20];;
    private String currentExp = "";
    private String currentCompany = "Don't Want to share";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        profession_array = getResources().getStringArray(R.array.professions);
        exp_array = getResources().getStringArray(R.array.experience);

        binding.editTextFullName.setText(pref.getString(ProjectConstants.PREF_KEY_F_NAME, "NameDefault"));
        binding.editTextLastName.setText(pref.getString(ProjectConstants.PREF_KEY_L_NAME, "lastNameDefault"));

        binding.editTextEmail.setText(pref.getString(ProjectConstants.PREF_KEY_EMAIL, "Email no Found"));
        binding.editTextUsername.setText(pref.getString(ProjectConstants.PREF_KEY_USERNAME, "Username no Found"));

        Glide.with(this).load(PROFILE_IMAGES+pref.getString(ProjectConstants.PREF_KEY_PICTURE, "NameDefault"))
                .placeholder(R.mipmap.default_cover).into(binding.imageViewUserDisplayPicture);
        Glide.with(this).load(PROFILE_IMAGES+pref.getString(ProjectConstants.PREF_KEY_COVER, "NameDefault"))
                .placeholder(R.mipmap.default_cover).into(binding.imageViewCover);

        binding.editTextCurrentCompany.setText(pref.getString(ProjectConstants.PREF_KEY_CURRENT_COMPANY, "Rather not say"));
        //setSwitch
        String privacy = pref.getString(ProjectConstants.PREF_KEY_IS_PRIVATE, "2");
        if(privacy.equals("2")){
            binding.switchMakePublic.setChecked(false);
            makePublicSwitch = "2";
        }else if(privacy.equals("1")){
            binding.switchMakePublic.setChecked(true);
            makePublicSwitch = "1";
        }

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
                ImageCropper(1);
            }
        });

        binding.imageViewCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageCropper(2);
            }
        });

        binding.imageViewUserDisplayPictureEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageCropper(1);
            }
        });
        binding.imageViewUserCoverPictureEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageCropper(2);
            }
        });

        binding.switchMakePublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(makePublicSwitch.equals("1")){
                    createAlertDialog("Make Account Private", getString(R.string.makeAccount_private),"Make Private", "2");
                }else if(makePublicSwitch.equals("2")){

                    createAlertDialog("Make Account Public", getString(R.string.makeAccount_public),"Make Public", "1");
                }
            }
        });
        setSpinner();
        getCurrentData();
    }

    private void setSpinner() {

        Spinner spinner = binding.spinnerProfession;
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, profession_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentProfession = profession_array[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                currentProfession = pref.getString(ProjectConstants.PREF_KEY_PROFESSION, "NA");
            }
        });

        Spinner spinner1 = binding.spinnerWorkExperience;
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, exp_array);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter2);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentExp = exp_array[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                currentExp = pref.getString(ProjectConstants.PREF_KEY_EXPERIENCE, "NA");
            }
        });

        int index=selectSpinnerValue(profession_array,pref.getString(ProjectConstants.PREF_KEY_PROFESSION, "NA"));
        Log.d(TAG, "setSpinner: Profession INdex: "+ index);
        Log.d(TAG, "setSpinner: Profession INdex: "+ profession_array[index]);
        spinner.setSelection(index, false);

        int index2=selectSpinnerValue(exp_array,pref.getString(ProjectConstants.PREF_KEY_EXPERIENCE, "NA"));
        Log.d(TAG, "setSpinner: Experience INdex: "+ index2);
        Log.d(TAG, "setSpinner: Experience INdex: "+ exp_array[index2]);
        spinner1.setSelection(index2, false);
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
        bundle.putString("l_name", binding.editTextLastName.getText().toString().trim());
        bundle.putString("privacy", makePublicSwitch);

        bundle.putByteArray("picture", getFileDataFromDrawable(1));
        bundle.putByteArray("coverpic", getFileDataFromDrawable(2));

        bundle.putString("profession", currentProfession);
        bundle.putString("experience", currentExp);

        Log.e(TAG, "update: profession: "+currentProfession );
        Log.e(TAG, "update: experience: "+currentExp );

        String current = binding.editTextCurrentCompany.getText().toString();

        if(current.length()==0){
            bundle.putString("current_company", currentCompany);
        }else{
            bundle.putString("current_company", current);
        }


        RestCalls restCalls = new RestCalls(this);
        restCalls.updateUserProfile(bundle);

    }

    private void ImageCropper(int pickPictureFlag) {
        this.pickPictureFlag = pickPictureFlag;
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

                    //imageBitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                if(pickPictureFlag==1){
                    Glide.with(this).load(resultUri).into(binding.imageViewUserDisplayPicture);
                }else if(pickPictureFlag==2){
                    Glide.with(this).load(resultUri).into(binding.imageViewCover);
                }



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public byte[] getFileDataFromDrawable(int pictureType) {
        Bitmap bitmap;
        if (pictureType == 1) {
            bitmap = ((BitmapDrawable) binding.imageViewUserDisplayPicture.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            Log.d(TAG, "getFileDataFromDrawable: image: " + byteArrayOutputStream.toByteArray());
            return byteArrayOutputStream.toByteArray();
        }else{
            bitmap = ((BitmapDrawable) binding.imageViewCover.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            Log.d(TAG, "getFileDataFromDrawable: image: " + byteArrayOutputStream.toByteArray());
            return byteArrayOutputStream.toByteArray();
        }

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
            editor.putString(ProjectConstants.PREF_KEY_COVER, response.get("coverpic"));
            editor.putString(ProjectConstants.PREF_KEY_IS_PRIVATE, response.get("privacy"));

            editor.putString(ProjectConstants.PREF_KEY_PROFESSION, response.get("profession"));
            editor.putString(ProjectConstants.PREF_KEY_EXPERIENCE, response.get("experience"));
            editor.putString(ProjectConstants.PREF_KEY_CURRENT_COMPANY, response.get("current_company"));

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

    private void createAlertDialog(String title, String message, String pButtonText, final String switchValue){
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_main)
                .setPositiveButton(pButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        makePublicSwitch = switchValue;
                    }
                })
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(binding.switchMakePublic.isChecked()){
                            binding.switchMakePublic.setChecked(false);
                        }else{
                            binding.switchMakePublic.setChecked(true);
                        }

                    }
                });
        alert.show();
    }

    private int selectSpinnerValue( String[] ListSpinner,String myString)
    {
        int index = 0;
        for(int i = 0; i < ListSpinner.length; i++){
            if(ListSpinner[i].equals(myString)){
                index=i;
                break;
            }
        }
        return index;
    }
}
