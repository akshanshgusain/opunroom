package org.akshanshgusain.killmonger2test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import static org.akshanshgusain.killmonger2test.LoginRegister.LoginActivity.mHeader;

public class Utils {

    public static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(4 + 1);
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }

    public static Bitmap getBitmapFromByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public String getVolleyErrorString(VolleyError error) {
        if (error instanceof TimeoutError) {
            return "Timeout Error";
        } else if (error instanceof NoConnectionError) {
            return "No Internet Connection";
        } else if (error instanceof AuthFailureError) {
            return "Authentication Error";
        } else if (error instanceof ServerError) {
            return "Server Error";
        } else if (error instanceof NetworkError) {
            return "Network Error";
        } else if (error instanceof ParseError) {
            return "Parse Error";
        }
        return " ";
    }
}
