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
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityEnterDetails2Binding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.akshanshgusain.killmonger2test.ProjectConstants.BUNDLE_REGISTER;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_FIRST_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_LAST_NAME_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PICTURE_WORKSPACE;

public class EnterDetails2Activity extends AppCompatActivity {
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
       binding.imageViewUserDisplayPicture.setOnClickListener(new View.OnClickListener() {
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
         }else{
             Toast.makeText(this, "works!", Toast.LENGTH_SHORT).show();
              bundle.putByteArray(INTENT_PICTURE_WORKSPACE, getFileDataFromDrawable(imageBitMap));
             bundle.putString(INTENT_FIRST_NAME_WORKSPACE, binding.editTextFirstName.getText().toString().trim());
             bundle.putString(INTENT_LAST_NAME_WORKSPACE, binding.editTextLastName.getText().toString().trim());
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
