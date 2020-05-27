package org.akshanshgusain.killmonger2test.SwipableViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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

import org.akshanshgusain.killmonger2test.R;
import org.akshanshgusain.killmonger2test.SendPicture.PreviewActivity;


import java.io.File;
import java.util.Objects;

import static android.view.View.GONE;


public class CameraFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {
    public static final int FLASH_ON = 0;
    public static final int FLASH_OFF = 1;
    public static final int FLASH_AUTO = 2;
    private int current_flash_state = 1;

    private CameraView camera;
    private ImageView mShutter, mFlip, mFlash;
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
        mCounterLayout = view.findViewById(R.id.constraintLayout_recording);

        cameraSetUP();


        mFlip.setOnClickListener(this);
        mFlash.setOnClickListener(this);
        mShutter.setOnTouchListener(this);
        mShutter.setOnLongClickListener(this);
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
                            Toast.makeText(getActivity(), "File Saved to: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), PreviewActivity.class);
                            i.putExtra("imagePreview", file.getAbsolutePath());
                            startActivity(i);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Check Your storage permissions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                super.onVideoTaken(result);

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
              if(current_flash_state==0){
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
                Log.d(TAG, "onTick: " + (int) l / 1000);
                mCounter.setText("00:" + (int) l / 1000);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
                mCounterLayout.setVisibility(GONE);
            }
        }.start();
    }

}
