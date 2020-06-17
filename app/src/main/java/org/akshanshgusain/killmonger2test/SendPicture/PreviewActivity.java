package org.akshanshgusain.killmonger2test.SendPicture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.internal.$Gson$Preconditions;
import com.shawnlin.numberpicker.NumberPicker;


import org.akshanshgusain.killmonger2test.Network.Company;
import org.akshanshgusain.killmonger2test.Network.Friends;
import org.akshanshgusain.killmonger2test.Network.Friends2;
import org.akshanshgusain.killmonger2test.Network.Groups;
import org.akshanshgusain.killmonger2test.Network.RestCalls;
import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.databinding.ActivityPreviewBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.akshanshgusain.killmonger2test.ProjectConstants.INTENT_PICTURE_WORKSPACE;
import static org.akshanshgusain.killmonger2test.ProjectConstants.PREF_KEY_ID;
import static org.akshanshgusain.killmonger2test.SendPicture.AdapterContacts.COMPANY_TYPE;
import static org.akshanshgusain.killmonger2test.SendPicture.AdapterContacts.GROUP_TYPE;
import static org.akshanshgusain.killmonger2test.SendPicture.AdapterContacts.SELF_TYPE;
import static org.akshanshgusain.killmonger2test.SwipableViews.CameraFragment.*;

public class PreviewActivity extends AppCompatActivity implements
        RestCalls.GetFriendsList2I, AdapterContacts.AdapterClickListener, BottomSheetFragment.SendButtonClick, RestCalls.CreateStoryI {

    private ActivityPreviewBinding binding;
    private static final String TAG = "PreviewTag";
    BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
    private LinkedHashSet<SingleContact> selectedItems = new LinkedHashSet<>();
    SharedPreferences pref;
    RestCalls restCalls;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    Uri fileUri;
    String mediaType;
    String filePathMedia;
    String videoDownloadURI;
    ProgressDialog mProgressDialog;
    boolean response1, response2 = false;

    final String[] data = {"1 Hour","6 Hours", "12 Hours", "1 Day", "2 Days"};
    final String[] dataOriginal = {"1","6", "12", "24", "48"};
    NumberPicker numberPicker;

    private String currentTimerSelection = "48";

    final AnimatorSet mAnimationSet = new AnimatorSet();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview);
        mAuth = FirebaseAuth.getInstance();

        numberPicker = binding.numberPickerPreview;
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(4);

        numberPicker.setFadingEdgeEnabled(true);
        numberPicker.setScrollerEnabled(true);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setAccessibilityDescriptionEnabled(true);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("Videos");
        mProgressDialog = new ProgressDialog(this);
        mediaType = getIntent().getStringExtra("type");

        filePathMedia = getIntent().getStringExtra("mediaPreview");
        Log.d(TAG, "onCreate: "+filePathMedia);

        getURI(filePathMedia);




        showPreview();

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                //call the service to get friend List
                pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
                restCalls = new RestCalls(PreviewActivity.this);
                restCalls.getFriendsList2(pref.getString(PREF_KEY_ID, ""));
            }
        });

        binding.imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.imageViewTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimer();
            }
        });

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentTimerSelection = dataOriginal[newVal-1];
            }
        });

        binding.imageViewPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.constraintTimerNumberPicker.setVisibility(View.GONE);
            }
        });
    }


    //Show Preview Before Sending...
    private void showPreview() {
        if (mediaType.equals(MEDIA_TYPE_PICTURE) && filePathMedia != null) {
            binding.videoViewPreview.setVisibility(View.GONE);
            binding.imageViewPreview.setVisibility(View.VISIBLE);


            Glide.with(this).load(fileUri).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    ObjectAnimator fadeOut = ObjectAnimator.ofFloat(binding.imageViewTimer, "alpha",  1f, .3f);
                    fadeOut.setDuration(2000);
                    ObjectAnimator fadeIn = ObjectAnimator.ofFloat(binding.imageViewTimer, "alpha", .3f, 1f);
                    fadeIn.setDuration(2000);



                    mAnimationSet.play(fadeIn).after(fadeOut);

                    mAnimationSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnimationSet.start();
                        }
                    });
                    mAnimationSet.start();

                    return false;
                }
            }).into(binding.imageViewPreview);
        } else if (mediaType.equals(MEDIA_TYPE_VIDEO) && filePathMedia != null) {
            binding.videoViewPreview.setVisibility(View.VISIBLE);
            binding.imageViewPreview.setVisibility(View.GONE);
            binding.buttonSend.setClickable(false);

            mProgressDialog.setTitle("Processing Video");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            binding.videoViewPreview.setVideoURI(fileUri);
            binding.videoViewPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                    saveToFireBase();
                }
            });
            binding.videoViewPreview.start();
        }
    }

    private void getURI(String filePath) {

        fileUri = FileProvider.getUriForFile(this.getApplicationContext()
                , this.getApplicationContext().getPackageName() + ".provider", new File(filePath));
    }


    @Override
    protected void onStart() {
        super.onStart();
        signInFirebaseUser();
    }




    @Override
    public void errorRequest(Map<String, String> response) {
        Log.d(TAG, "errorRequest: " + response.get("VolleyError"));
        Log.d(TAG, "Exception: " + response.get("exception"));

    }


    @Override
    public void response(Friends2 response) {
        if (response != null) {
            bottomSheetFragment.dataFromParent(response);
        }
    }




    @Override
    public void adapterClickListener(int position, String id, SingleContact model) {
        selectedItems.add(model);
    }

    @Override
    public void sendButtonClick() {
        int count = 0;
        for (SingleContact temp : selectedItems) {
            if (temp.isSelected) {
                count++;
            }
        }
        if (count == 0) {
            Toast.makeText(this, "Please Select target", Toast.LENGTH_SHORT).show();
        } else {
            String selfIds = new String();
            String groupIds = new String();
            String companyId = new String();

            for (SingleContact temp : selectedItems) {
                if (temp.isSelected) {
                    switch (temp.getType()) {
                        case SELF_TYPE: {
                            selfIds = temp.getUserId();
                        }
                        break;
                        case GROUP_TYPE: {
                            groupIds = groupIds + "," + temp.getUserId();
                        }
                        break;
                        case COMPANY_TYPE: {
                            companyId = temp.getUserId();
                        }
                        break;
                    }
                }
            }
            //Call createStory API: >>>>
            Bundle bundle = new Bundle();
            bundle.putString("userid", pref.getString(PREF_KEY_ID, ""));
            bundle.putString("selfid", selfIds);
            bundle.putString("friends", "");
            bundle.putString("groups", groupIds);
            bundle.putString("company", companyId);
            bundle.putString("duaration", currentTimerSelection);
            if (mediaType.equals(MEDIA_TYPE_VIDEO)) {
                bundle.putString("video", videoDownloadURI);
            } else {
                bundle.putByteArray(INTENT_PICTURE_WORKSPACE, getFileDataFromDrawable(null));
            }
            restCalls.createStory(bundle, mediaType);

        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            bitmap = ((BitmapDrawable) binding.imageViewPreview.getDrawable()).getBitmap();

        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        Log.d(TAG, "getFileDataFromDrawable: image: " + byteArrayOutputStream.toByteArray());
        return byteArrayOutputStream.toByteArray();

    }

    @Override
    public void responseCS(Map<String, String> response) {
        if (response.get("status").equals("1")) {
            Toast.makeText(this, "Story Sent!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "SomeError Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void errorRequestCS(Map<String, String> response) {
        Toast.makeText(this, "" + response.toString(), Toast.LENGTH_SHORT).show();

    }

    ///Firebase:

    private void signInFirebaseUser() {
        mAuth.signInWithEmailAndPassword("akshansh.gusain@novostack.com", "9654829994")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUser = mAuth.getCurrentUser();
                            Log.d(TAG, "onComplete: Login Complete");
                        } else {
                            Toast.makeText(PreviewActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveToFireBase() {
        final StorageReference videoReff = mStorageRef.child(System.currentTimeMillis() + "_opunRooom_vid.mp4");

        videoReff.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                videoReff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: saved to :" + uri);
                        videoDownloadURI = uri.toString();
                        binding.buttonSend.setClickable(true);
                        mProgressDialog.dismiss();
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Save Failed");
                    }
                });
    }

    private void showTimer() {
        mAnimationSet.cancel();

        if(binding.constraintTimerNumberPicker.getVisibility() == View.VISIBLE){
            binding.constraintTimerNumberPicker.setVisibility(View.GONE);
        }else{
            binding.constraintTimerNumberPicker.setVisibility(View.VISIBLE);
        }

    }
}
