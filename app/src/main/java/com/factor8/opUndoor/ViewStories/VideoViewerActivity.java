package com.factor8.opUndoor.ViewStories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import com.factor8.opUndoor.R;
import com.factor8.opUndoor.databinding.ActivityVideoViewerBinding;
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
import java.io.File;

public class VideoViewerActivity extends AppCompatActivity {
    private ActivityVideoViewerBinding binding;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    Uri fileUri;
    private static final String TAG = "VideoViewerActivity";
    private VideoView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_video_viewer);
        String filePath = getIntent().getStringExtra("mediaPreview");
        player = binding.videoPlayer;
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Videos");
        getURI(filePath) ;

    }
    private void getURI(String filePath) {

        fileUri = FileProvider.getUriForFile(this.getApplicationContext()
                ,this.getApplicationContext().getPackageName()+".provider",new File(filePath));
    }

    @Override
    protected void onStart() {
        super.onStart();
        signInFirebaseUser();
    }
    private void signInFirebaseUser() {
        mAuth.signInWithEmailAndPassword("akshansh.gusain@novostack.com","9654829994")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mUser = mAuth.getCurrentUser();
                            Log.d(TAG, "onComplete: Login Complete");
                            saveToFireBase();
                        }else{
                            Toast.makeText(VideoViewerActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private void saveToFireBase() {
        final StorageReference imageRef = mStorageRef.child(System.currentTimeMillis()+"_opunRooom_vid.mp4");

        imageRef.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "onSuccess: Saved"+ taskSnapshot);
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "Download URI: "+ uri);
                            String uriString = uri.toString();
                        player.setVideoURI(Uri.parse(uriString));
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                Toast.makeText(VideoViewerActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
                                mediaPlayer.setLooping(true);
                            }
                        });
                        player.start();
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to Save");
                    }
                });
    }

}