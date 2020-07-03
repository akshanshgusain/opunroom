package org.akshanshgusain.killmonger2test.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityEnterDetails2Binding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.akshanshgusain.killmonger2test.ProjectConstants.BUNDLE_REGISTER;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_CURRENT_COMPANY_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_EXP_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_FIRST_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_LAST_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PICTURE_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PROFESSION_WORKSPACE;

public class EnterDetails2Activity extends AppCompatActivity {
    private String[] profession_array = new String[20];
    private String currentProfession = "";
    private String[] exp_array = new String[20];;
    private String currentExp = "";
    private String currentCompany = "Don't Want to share";

   private ActivityEnterDetails2Binding binding;
    Bitmap imageBitMap = null;
    private Bundle bundle;
    private static final String TAG = "EnterDetails2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_details_2);

        bundle = getIntent().getBundleExtra(BUNDLE_REGISTER);

        profession_array = getResources().getStringArray(R.array.professions);
        exp_array = getResources().getStringArray(R.array.experience);

       binding.imageViewUserDisplayPicture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ImageCropper();
           }
       });
       binding.imageViewUserDisplayPictureEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ImageCropper();
           }
       });
       binding.buttonSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               validate();
           }
       });
       setSpinner();
    }

    private void setSpinner() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, profession_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerProfession.setAdapter(adapter);
        binding.spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                     currentProfession = profession_array[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, exp_array);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerExp.setAdapter(adapter2);
        binding.spinnerExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentExp = exp_array[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void validate() {
         if(imageBitMap==null){
             binding.textViewError.setVisibility(View.VISIBLE);
             binding.textViewError.setText("Please Upload an Image");
         }else if(binding.editTextFirstName.getText().toString().isEmpty()){
             binding.textViewError.setVisibility(View.VISIBLE);
             binding.textViewError.setText("Please Enter your First Name");
         }
         else if(binding.editTextLastName.getText().toString().isEmpty()){
             binding.textViewError.setVisibility(View.VISIBLE);
             binding.textViewError.setText("Please Enter your Last Name");
         } else if(currentProfession==null){
             binding.textViewError.setVisibility(View.VISIBLE);
             binding.textViewError.setText("Please Select your feild of work");
         }else if(currentExp==null){
             binding.textViewError.setVisibility(View.VISIBLE);
             binding.textViewError.setText("Please Select your Work Experience");
         }
         else{


              bundle.putByteArray(INTENT_PICTURE_WORKSPACE, getFileDataFromDrawable(imageBitMap));
             bundle.putString(INTENT_FIRST_NAME_WORKSPACE, binding.editTextFirstName.getText().toString().trim());
             bundle.putString(INTENT_LAST_NAME_WORKSPACE, binding.editTextLastName.getText().toString().trim());
             bundle.putString(INTENT_PROFESSION_WORKSPACE, currentProfession);
             if(binding.editTextCurrentCompany.getText().toString().length()<=2){
                 bundle.putString(INTENT_CURRENT_COMPANY_WORKSPACE, currentCompany);
             }else{
                 bundle.putString(INTENT_CURRENT_COMPANY_WORKSPACE, binding.editTextCurrentCompany.getText().toString());
             }
             bundle.putString(INTENT_EXP_WORKSPACE, currentExp);
             Intent o  =new Intent(EnterDetails2Activity.this, TransitionActivity.class) ;
             o.putExtra(BUNDLE_REGISTER, bundle);
             startActivity(o);
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
}
