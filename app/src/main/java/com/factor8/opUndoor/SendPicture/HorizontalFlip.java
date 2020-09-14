package com.factor8.opUndoor.SendPicture;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

class HorizontalFlip extends BitmapTransformation {
    int cameraFacing;

    public HorizontalFlip(int cameraFacing) {
        this.cameraFacing = cameraFacing;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if(cameraFacing == 1){
            return flip(toTransform);
        }else{
            return toTransform;
        }
        //return null;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }

    private Bitmap flip(Bitmap d) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap src = d;
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        //return new BitmapDrawable(dst);
        return dst;
    }
}
