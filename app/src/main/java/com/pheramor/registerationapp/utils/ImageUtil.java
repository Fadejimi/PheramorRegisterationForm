package com.pheramor.registerationapp.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageUtil {
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static String encodeImage(byte[] imageArray) {
        return Base64.encodeToString(imageArray, Base64.DEFAULT);
    }

    public static byte[] decodeBytes(String encodedString) {
        String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
        return Base64.decode(pureBase64Encoded, Base64.DEFAULT);
    }
}
