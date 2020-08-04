package com.factor8.opUndoor.SwipableViews;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import android.os.Handler;
import android.provider.Settings;
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
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.factor8.opUndoor.R;
import com.factor8.opUndoor.SendPicture.PreviewActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
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


import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.util.List;
import java.util.Objects;


import id.zelory.compressor.Compressor;

import static android.view.View.GONE;


public class CameraFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {
    public static final int FLASH_ON = 0;
    public static final int FLASH_OFF = 1;
    public static final int FLASH_AUTO = 2;
    private int current_flash_state = 1;
    public static final String MEDIA_TYPE_PICTURE = "mediaTypePicture";
    public static final String MEDIA_TYPE_PICTURE2 = "mediaTypePicture2";
    public static final String MEDIA_TYPE_VIDEO = "mediaTypeVideo";
    public static final String CAMERA_FACING = "camera_facing";

    private CameraView camera;
    private ImageView mShutter, mFlip, mFlash, mGallery, mCancel;
    private static TextView mCounter;
    private ConstraintLayout mCounterLayout;
    private static final String TAG = "camFag";

    private boolean longPress = false;

    public static final long START_TIME_IN_MILLIS = 10000;
    private CountDownTimer mCountDownTimer;

    int cameraFacing = 0;
    private ButtonClickListener buttonClickListener;

    int askPermissions = 0;


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
        mCancel = view.findViewById(R.id.imageView_cancel_button);


        mFlip.setOnClickListener(this);
        mFlash.setOnClickListener(this);
        mShutter.setOnTouchListener(this);
        mShutter.setOnLongClickListener(this);
        mGallery.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        buttonClickListener = (ButtonClickListener) context;
    }

    //Camera Setup and callbacks
    private void cameraSetUP() {
        camera.setLifecycleOwner(Objects.requireNonNull(getActivity()));
        camera.setMode(Mode.PICTURE);
        camera.setRequestPermissions(false);
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
                        + File.separator + "Opundoor", System.currentTimeMillis() + "_" + "Opundoor.jpg");
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
                                        + File.separator + "Opundoor", "OpundoorRaw");

                                File compressedImage = new Compressor(getActivity())
                                        .setMaxWidth(640)
                                        .setMaxHeight(480)
                                        .setQuality(75)
                                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                        .setDestinationDirectoryPath(pictureDPAth.getAbsolutePath())
                                        .compressToFile(file);


                                //Delete the Original File
                                if (file.exists()) {
                                    if (file.delete())
                                        Log.d(TAG, "onFileReady: Original File is Deleted");
                                    else Log.d(TAG, "onFileReady: Original File is not Deleted");
                                }

                                Intent i = new Intent(getActivity(), PreviewActivity.class);
                                i.putExtra("type", MEDIA_TYPE_PICTURE);
                                i.putExtra(CAMERA_FACING, cameraFacing);
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
                i.putExtra(CAMERA_FACING, 0);
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

        if(askPermissions == 0)
        {
            checkForPermissions();
        }

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
                    cameraFacing = 0;
                    mFlash.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_flash_off));
                } else {
                    camera.setFacing(Facing.FRONT);
                    mFlash.setClickable(false);
                    cameraFacing = 1;
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
                if (
                        (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                                &&
                                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                ) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(getActivity());
                } else {
                    checkForPermissions();
                }

            }
            break;

            case R.id.imageView_cancel_button: {
                buttonClickListener.buttonClickListener(1);
            }
            break;
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
                    mCancel.setVisibility(View.VISIBLE);
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

            File videoDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + File.separator + "Opundoor", "Video");
            if (!videoDirectory.exists()) {
                videoDirectory.mkdirs();
            }
            camera.takeVideoSnapshot(new File(videoDirectory, System.currentTimeMillis() + "_" + "video.mp4"));
            longPress = true;
            timerHandler();
            return false;
        }
        return false;
    }

    private void timerHandler() {
        mCounterLayout.setVisibility(View.VISIBLE);
        mCancel.setVisibility(GONE);
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

//    ///Permissions
//    private void checkForPermissions() {
//        Dexter.withContext(getActivity())
//                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                      //  Toast.makeText(getActivity(), permissionGrantedResponse.getPermissionName(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//                        if (permissionDeniedResponse.isPermanentlyDenied()) {
//                            Toast.makeText(getActivity(), "Go to Settings ", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getActivity(), "Permission denied " + permissionDeniedResponse.getPermissionName(), Toast.LENGTH_SHORT).show();
//                            PermissionListener dialogPermissionListener =
//                                    DialogOnDeniedPermissionListener.Builder
//                                            .withContext(getActivity())
//                                            .withTitle("Storage Permission is Required")
//                                            .withMessage("Storage permission is needed to save pictures")
//                                            .withButtonText(android.R.string.ok)
//                                            .withIcon(R.drawable.ic_main)
//                                            .build();
//
//
//                        }
//                    }
//
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                        Toast.makeText(getActivity(), "onPermissionRationaleShouldBeShown", Toast.LENGTH_SHORT).show();
//
//                        permissionToken.continuePermissionRequest();
//                    }
//                }).onSameThread().check();
//
//
//    }


    private void checkForPermissions() {
        if (
                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        ||
                        (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        ||
                        (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        ||
                        (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        ) {
            //Any for the permissions are not granted
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Need Permissions");
            builder.setIcon(R.drawable.ic_main);
            builder.setMessage("opUndoor require Camera, Record-Audio & Storage permissions in order to function. Denying these permissions may cause the app to not function properly." +
                    "Please provide the necessary permissions.");
            builder.setPositiveButton("Give Permissions", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    dexterPermissions();
                }
            });
            builder.setNegativeButton("I rather not", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            cameraSetUP();
        }


    }

    private void dexterPermissions() {
        Dexter.withContext(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // init camera
                            cameraSetUP();


                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                                askPermissions = 1;
                                showSettingsDialog();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError dexterError) {

            }
        })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setIcon(R.drawable.ic_main);
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
                askPermissions = 0;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                askPermissions = 0;
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


}
