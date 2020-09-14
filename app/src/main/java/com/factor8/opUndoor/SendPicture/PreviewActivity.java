package com.factor8.opUndoor.SendPicture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.ActivityPreviewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shawnlin.numberpicker.NumberPicker;


import com.factor8.opUndoor.Network.Responses.Friends2;
import com.factor8.opUndoor.Network.RestCalls;
import com.factor8.opUndoor.SendPicture.PictureEditor.EmojiFragment;
import com.factor8.opUndoor.SendPicture.PictureEditor.FilterListener;
import com.factor8.opUndoor.SendPicture.PictureEditor.FilterViewAdapter;
import com.factor8.opUndoor.SendPicture.PictureEditor.TextEditorFragment;
import com.factor8.opUndoor.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashSet;
import java.util.Map;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;
import ja.burhanrashid52.photoeditor.ViewType;

import static android.view.View.*;
import static com.factor8.opUndoor.ProjectConstants.INTENT_PICTURE_WORKSPACE;
import static com.factor8.opUndoor.ProjectConstants.PREF_KEY_ID;
import static com.factor8.opUndoor.SendPicture.AdapterContacts.COMPANY_TYPE;
import static com.factor8.opUndoor.SendPicture.AdapterContacts.GROUP_TYPE;
import static com.factor8.opUndoor.SendPicture.AdapterContacts.SELF_TYPE;
import static com.factor8.opUndoor.SwipableViews.CameraFragment.*;

public class PreviewActivity extends AppCompatActivity implements
        RestCalls.GetFriendsList2I, AdapterContacts.AdapterClickListener, BottomSheetFragment.SendButtonClick, RestCalls.CreateStoryI, EmojiFragment.EmojiListener, FilterListener {

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

    private PhotoEditor mPhotoEditor;
    private boolean mIsFilterVisible;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    private ConstraintLayout mRootView;
    private RecyclerView mRvFilters;
    private FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);
    Uri mSaveImageUri;

    private ProgressDialog progressDialog;

    private int cameraFacing = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview);
        mAuth = FirebaseAuth.getInstance();
        Log.e(TAG, "onCreate: PreviewActivity");

        mRootView = binding.rootView;

        mRvFilters = findViewById(R.id.rvFilterView);
        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvFilterView.setLayoutManager(llmFilters);
        binding.rvFilterView.setAdapter(mFilterViewAdapter);

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
        cameraFacing = getIntent().getIntExtra(CAMERA_FACING,0);
        Log.d(TAG, "onCreate: "+filePathMedia);

        getURI(filePathMedia);

        setupPhotoEditor();

        showPreview();



        binding.buttonSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItems.clear();
               // if(mediaType.equals(MEDIA_TYPE_PICTURE)){

                    showBottomSheet();

            }
        });

        binding.imageViewCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.imageViewTimer.setOnClickListener(new OnClickListener() {
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

        binding.imageViewPreview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.constraintTimerNumberPicker.setVisibility(GONE);
            }
        });

        binding.videoViewPreview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.constraintTimerNumberPicker.setVisibility(GONE);
            }
        });

        binding.imageViewTextEditor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateTextEditor();
            }
        });

        binding.imageViewSticker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateEmojiEditor();
            }
        });

        binding.imageViewFilters.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilter(true);
            }
        });

    }



    private void showBottomSheet() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

        //call the service to get friend List
        pref = getApplicationContext().getSharedPreferences("LoginPreference", MODE_PRIVATE);
        restCalls = new RestCalls(PreviewActivity.this);
        restCalls.getFriendsList2(pref.getString(PREF_KEY_ID, ""));
    }


    //Show Preview Before Sending...
    private void showPreview() {
        if (mediaType.equals(MEDIA_TYPE_PICTURE) && filePathMedia != null) {
            binding.videoViewPreview.setVisibility(GONE);
            binding.imageViewPreview.setVisibility(VISIBLE);


            Glide.with(this).load(fileUri).transform(new HorizontalFlip(getIntent().getIntExtra(CAMERA_FACING,0))).listener(new RequestListener<Drawable>() {
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
            }).into(binding.imageViewPreview.getSource());

        } else if (mediaType.equals(MEDIA_TYPE_VIDEO) && filePathMedia != null) {
            binding.imageViewTextEditor.setVisibility(GONE);
            binding.imageViewSticker.setVisibility(GONE);
            binding.imageViewFilters.setVisibility(GONE);
            binding.videoViewPreview.setVisibility(VISIBLE);
            binding.imageViewPreview.setVisibility(GONE);
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
            Log.e(TAG, "selected Item : "+ temp.username );
            if (temp.isSelected) {
                count++;
            }
        }
        if (count == 0) {
            Toast.makeText(this, "Please Select target", Toast.LENGTH_SHORT).show();
        } else {
            //Save Image To External Storage
            if (mediaType.equals(MEDIA_TYPE_VIDEO)) {
                makeNetworkCall();
            }else{
                saveImage();
            }

        }
    }

    private void makeNetworkCall(){
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
        Log.e(TAG, "sendButtonClick: Users "+ selfIds);
        Log.e(TAG, "sendButtonClick: groupIds"+ groupIds );
        Log.e(TAG, "sendButtonClick: CompanyIDs"+ companyId );

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


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            bitmap = ((BitmapDrawable) binding.imageViewPreview.getSource().getDrawable()).getBitmap();

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

        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                 long totalBytes = taskSnapshot.getTotalByteCount();
                 long bytesDone = taskSnapshot.getBytesTransferred();
                 progressCalculator(totalBytes, bytesDone);
                Log.d("Progress", "onProgress: totalByte: "+ totalBytes);
                Log.d("Progress", "onProgress: DoneByte: "+ bytesDone);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Save Failed");
                        mProgressDialog.dismiss();
                        Toast.makeText(PreviewActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showTimer() {
        mAnimationSet.cancel();

        if(binding.constraintTimerNumberPicker.getVisibility() == VISIBLE){
            binding.constraintTimerNumberPicker.setVisibility(GONE);
        }else{
            binding.constraintTimerNumberPicker.setVisibility(VISIBLE);
        }

    }


    private void setupPhotoEditor() {
        mPhotoEditor = new PhotoEditor.Builder(this, binding.imageViewPreview).build();
        mPhotoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener() {
            @Override
            public void onEditTextChangeListener(final View rootView, String text, int colorCode) {

                TextEditorFragment textEditorDialogFragment = TextEditorFragment.show(PreviewActivity.this, text, colorCode);
                textEditorDialogFragment.setOnTextEditorListener(new TextEditorFragment.TextEditor() {
                    @Override
                    public void onDone(String inputText, int colorCode) {
                        final TextStyleBuilder styleBuilder =  new TextStyleBuilder();
                        styleBuilder.withTextSize(40f);
                        styleBuilder.withTextColor(colorCode);
                        styleBuilder.withGravity(Gravity.CENTER);
                        styleBuilder.withBackgroundColor(getColor(R.color.colorBlackTransparent));
                        mPhotoEditor.editText(rootView, inputText, styleBuilder);
                    }
                });
            }

            @Override
            public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

            }

            @Override
            public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

            }

            @Override
            public void onStartViewChangeListener(ViewType viewType) {

            }

            @Override
            public void onStopViewChangeListener(ViewType viewType) {

            }
        });

        showFilter(false);

    }

    private void inflateTextEditor() {

        TextEditorFragment textEditorDialogFragment = TextEditorFragment.show(this);
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode) {
                TextStyleBuilder styleBuilder =  new TextStyleBuilder();
                styleBuilder.withTextSize(40f);
                styleBuilder.withTextColor(colorCode);
                styleBuilder.withGravity(Gravity.CENTER);
                styleBuilder.withBackgroundColor(getColor(R.color.colorBlackTransparent));
                mPhotoEditor.addText(inputText, styleBuilder);
            }
        });
    }

    private void inflateEmojiEditor() {
        EmojiFragment emojiFragment = new EmojiFragment();
        emojiFragment.setEmojiListener(this);
        emojiFragment.show(getSupportFragmentManager(), emojiFragment.getTag());
    }

    @Override
    public void onEmojiClick(String emojiUnicode) {
        mPhotoEditor.addEmoji(emojiUnicode);
    }


    void showFilter(boolean isVisible) {
        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mRootView);

        if (isVisible) {
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
        } else {
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.END);
        }

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);

        mConstraintSet.applyTo(mRootView);
    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }

    @Override
    public void onBackPressed() {
        if (mIsFilterVisible) {
            showFilter(false);

        }else{
            super.onBackPressed();
        }
    }

    @SuppressLint("MissingPermission")
    private void saveImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        if (Utils.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    progressDialog.show();

            File pictureDPAth = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + File.separator + "Opundoor" + File.separator + "Sent" );

            if(!pictureDPAth.exists()){
                boolean dirCreated = pictureDPAth.mkdirs();
                Log.d(TAG, "New Directory Created");
            }

            try {
                File f = new File(pictureDPAth, System.currentTimeMillis()+"Opundoor_sent" + ".png");
                f.createNewFile();


                SaveSettings saveSettings = new SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build();

                mPhotoEditor.saveAsFile(f.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        progressDialog.hide();
                        mSaveImageUri = Uri.fromFile(new File(imagePath));
                        binding.imageViewPreview.getSource().setImageURI(mSaveImageUri);
                        makeNetworkCall();
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.hide();
                        Log.e(TAG, "onFailure: Fail to save Image" +exception);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                progressDialog.hide();
                Log.d(TAG, "saveImage: "+e);
            }
        }
    }

    private void progressCalculator(long totalBytes, long doneBytes){
        if(totalBytes>0){
            double proportion = ((double)doneBytes/(double)totalBytes);

            float donePercentage = (float)(100 * proportion);
            DecimalFormat df = new DecimalFormat("#00.0");

            mProgressDialog.setMessage(df.format(donePercentage)+"% Done");
        }
    }

}
