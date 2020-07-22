package org.akshanshgusain.killmonger2test.SwipableViews;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.gesture.Gesture;
import com.otaliastudios.cameraview.gesture.GestureAction;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.SendPicture.PreviewActivity;
import org.akshanshgusain.killmonger2test.ViewStories.VideoViewerActivity;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;


public class CameraFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {
    public static final int FLASH_ON = 0;
    public static final int FLASH_OFF = 1;
    public static final int FLASH_AUTO = 2;
    private int current_flash_state = 1;
    public static final String MEDIA_TYPE_PICTURE = "mediaTypePicture";
    public static final String MEDIA_TYPE_PICTURE2 = "mediaTypePicture2";
    public static final String MEDIA_TYPE_VIDEO = "mediaTypeVideo";

    private CameraView camera;
    private ImageView mShutter, mFlip, mFlash, mGallery;
    private static TextView mCounter;
    private ConstraintLayout mCounterLayout;
    private static final String TAG = "camFag";

    private boolean longPress = false;

    public static final long START_TIME_IN_MILLIS = 10000;
    private CountDownTimer mCountDownTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        camera = view.findViewById(R.id.camera);
        mShutter = view.findViewById(R.id.imageView_shutter);
        mFlip = view.findViewById(R.id.imageView_flip);
        mFlash = view.findViewById(R.id.imageView_flash);
        mCounter = view.findViewById(R.id.timer);
        mGallery = view.findViewById(R.id.imageView_gallery);
        mCounterLayout = view.findViewById(R.id.constraintLayout_recording);

        cameraSetUP();


        mFlip.setOnClickListener(this);
        mFlash.setOnClickListener(this);
        mShutter.setOnTouchListener(this);
        mShutter.setOnLongClickListener(this);
        mGallery.setOnClickListener(this);
        checkForPermissions();
        return view;
    }


    //Camera Setup and callbacks
    private void cameraSetUP() {
        camera.setLifecycleOwner(Objects.requireNonNull(getActivity()));
        camera.setMode(Mode.PICTURE);
        camera.setFlash(Flash.OFF);
        camera.mapGesture(Gesture.PINCH, GestureAction.ZOOM);
        camera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS);
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onCameraError(@NonNull CameraException exception) {
                super.onCameraError(exception);
            }

            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                super.onPictureTaken(result);

                File pictureFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + File.separator + "OpunRoom", System.currentTimeMillis() + "_" + "OpunRoom.jpg");
                boolean success = true;
                if (!pictureFile.exists()) {
                    success = pictureFile.mkdirs();
                }
                if (success) {

                    result.toFile(pictureFile, new FileCallback() {
                        @Override
                        public void onFileReady(@Nullable File file) {
                            try {
                                File pictureDPAth = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                        + File.separator + "OpunRoom", "Sent");

                                File compressedImage = new Compressor(getActivity())
                                        .setMaxWidth(640)
                                        .setMaxHeight(480)
                                        .setQuality(75)
                                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                        .setDestinationDirectoryPath(pictureDPAth.getAbsolutePath())
                                        .compressToFile(file);


                                //Delete the Original File
                                if(file.exists()){
                                   if (file.delete()) Log.d(TAG, "onFileReady: Original File is Deleted");
                                   else  Log.d(TAG, "onFileReady: Original File is not Deleted");
                                }

                                Intent i = new Intent(getActivity(), PreviewActivity.class);
                                i.putExtra("type", MEDIA_TYPE_PICTURE);
                                i.putExtra("mediaPreview", compressedImage.getAbsolutePath());
                                startActivity(i);

                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d(TAG, "onFileReady: " + e);
                            }


                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Check Your storage permissions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                super.onVideoTaken(result);
//                 //Compression ---------     START      -------------------------
//                String inputVideoPath = result.getFile().getAbsolutePath();
//                FFmpeg ffmpeg = FFmpeg.getInstance(getActivity());
//                try {
//                    //Load the binary
//                    ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
//
//                        @Override
//                        public void onStart() {
//                            Log.d(TAG, "onStart: ");
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            Log.d(TAG, "onFailure: ");
//                        }
//
//                        @Override
//                        public void onSuccess() {
//                            Log.d(TAG, "onSuccess: ");
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            Log.d(TAG, "onFinish: ");
//                        }
//                    });
//                } catch (FFmpegNotSupportedException e) {
//                    // Handle if FFmpeg is not supported by device
//                    Log.d(TAG, "onVideoTaken: FFmpeg not support by the device: "+e);
//                }
//
//                try {
//                    // to execute "ffmpeg -version" command you just need to pass "-version"
//                    String outputPath =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                            + File.separator + "OpunRoom","OpunRoomVidC.mp4").toString();
//
//                    String[] commandArray = new String[]{};
//                    commandArray = new String[]{"-y", "-i", inputVideoPath, "-s", "1920x1080", "-r", "30",
//                            "-vcodec", "mpeg4", "-b:v", "300k", "-b:a", "48000", "-ac", "2", "-ar", "22050", outputPath};
//
//                    ffmpeg.execute(commandArray, new ExecuteBinaryResponseHandler() {
//                        @Override
//                        public void onStart() {
//                            Log.d("FFmpeg", "onStart");
//
//                        }
//                        @Override
//                        public void onProgress(String message) {
//                            Log.d("FFmpeg onProgress? ", message);
//                        }
//                        @Override
//                        public void onFailure(String message) {
//                            Log.d("FFmpeg onFailure? ", message);
//                        }
//                        @Override
//                        public void onSuccess(String message) {
//                            Log.d("FFmpeg onSuccess? ", message);
//
//                        }
//                        @Override
//                        public void onFinish() {
//                            Log.d("FFmpeg", "onFinish");
//
//                        }
//                    });
//                } catch (FFmpegCommandAlreadyRunningException e) {
//                    e.printStackTrace();
//                    Log.d(TAG, "onVideoTaken: "+e);
//                    // Handle if FFmpeg is already running
//                }
//
//                //Compression ---------     END      -------------------------

                Intent i = new Intent(getActivity(), PreviewActivity.class);
                i.putExtra("type", MEDIA_TYPE_VIDEO);
                i.putExtra("mediaPreview", result.getFile().getAbsolutePath());
                startActivity(i);


            }

            @Override
            public void onVideoRecordingStart() {
                super.onVideoRecordingStart();
            }

            @Override
            public void onVideoRecordingEnd() {
                super.onVideoRecordingEnd();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        camera.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        camera.close();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_shutter: {
                Log.d(TAG, "onClick: onClick");

            }
            break;
            case R.id.imageView_flip: {
                if (camera.getFacing() == Facing.FRONT) {
                    camera.setFacing(Facing.BACK);
                    mFlash.setClickable(true);
                    mFlash.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_flash_off));
                } else {
                    camera.setFacing(Facing.FRONT);
                    mFlash.setClickable(false);
                    mFlash.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_flash_off));
                }
            }
            break;
            case R.id.imageView_flash: {
                switch (current_flash_state) {
                    case 0:
                        camera.setFlash(Flash.OFF);
                        mFlash.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_flash_off));
                        current_flash_state = 1;
                        break;
                    case 1:
                        camera.setFlash(Flash.AUTO);
                        mFlash.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_flash_auto));
                        current_flash_state = 2;
                        break;
                    case 2:
                        camera.setFlash(Flash.ON);
                        mFlash.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_flash_on));
                        current_flash_state = 0;
                        break;
                }
            }
            break;
            case R.id.imageView_gallery: {
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(9, 16)
//                        .start(getActivity());
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getActivity());
            }break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.imageView_shutter) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                // Log.d(TAG, "onTouch: Action_down");
                return false;

            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (!longPress) {
                    Log.d(TAG, "Click Picture...............");
                    camera.takePicture();
                } else {
                    Log.d(TAG, "Stop Video");
                    mCountDownTimer.cancel();
                    mCounterLayout.setVisibility(GONE);
                    longPress = false;
                    if (camera.isTakingVideo()) {
                        camera.stopVideo();
                        camera.setFlash(Flash.OFF);
                    }
                }

                return false;
            }
        }
        return false;
    }


    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.imageView_shutter) {
            Log.d(TAG, "Stat Video:.....");
            if (current_flash_state == 0) {
                camera.setFlash(Flash.TORCH);
            }
            camera.takeVideoSnapshot(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + File.separator + "OpunRoom", System.currentTimeMillis() + "_" + "video.mp4"));
            longPress = true;
            timerHandler();
            return false;
        }
        return false;
    }

    private void timerHandler() {
        mCounterLayout.setVisibility(View.VISIBLE);
        mCountDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long l) {
                // Log.d(TAG, "onTick: " + (int) l / 1000);
                mCounter.setText("00:" + (int) l / 1000);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
                mCounterLayout.setVisibility(GONE);
            }
        }.start();
    }

    ///Permissions
    private void checkForPermissions() {




        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                      //  Toast.makeText(getActivity(), permissionGrantedResponse.getPermissionName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if (permissionDeniedResponse.isPermanentlyDenied()) {
                            Toast.makeText(getActivity(), "Go to Settings ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Permission denied " + permissionDeniedResponse.getPermissionName(), Toast.LENGTH_SHORT).show();
                            PermissionListener dialogPermissionListener =
                                    DialogOnDeniedPermissionListener.Builder
                                            .withContext(getActivity())
                                            .withTitle("Storage Permission is Required")
                                            .withMessage("Storage permission is needed to save pictures")
                                            .withButtonText(android.R.string.ok)
                                            .withIcon(R.drawable.ic_main)
                                            .build();
                            dialogPermissionListener.onPermissionDenied(permissionDeniedResponse);

                        }
                    }


                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        Toast.makeText(getActivity(), "onPermissionRationaleShouldBeShown", Toast.LENGTH_SHORT).show();

                        permissionToken.continuePermissionRequest();
                    }
                }).onSameThread().check();


    }

//  private File flippImage(File file){
//      String filePath = file.getPath();
//      Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//
//      Matrix matrix = new Matrix();
//      matrix.preScale(-1.0f, 1.0f);
//      Bitmap filppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//
//  }


}
