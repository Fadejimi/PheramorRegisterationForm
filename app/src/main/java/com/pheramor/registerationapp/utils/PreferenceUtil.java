package com.pheramor.registerationapp.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pheramor.registerationapp.retrofit.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class PreferenceUtil {
    private static final String KEY = "user_pref";
    private static final String IMAGE_KEY = "image_pref";
    private static final String IMAGE_PATH = "image_path";
    public static final String PREF = "my_pref";
    public static final int FIRST_FORM = 1;
    public static final int SECOND_FORM = 2;
    private static Gson gson = new Gson();

    public static User getUser(SharedPreferences pref) {
        String userString = pref.getString(KEY, null);
        User user;
        if (userString != null) {
            user = gson.fromJson(userString, User.class);
            return user;
        }

        return null;
    }

    /*public static void putImage(SharedPreferences pref, byte[] imageBytes) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(IMAGE_KEY, ImageUtil.encodeImage(imageBytes));
        editor.apply();
    }*/

    public static void putImagePath(SharedPreferences pref, String imagePath) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(IMAGE_PATH, imagePath);
        editor.apply();
    }

    public static String getImagePath(SharedPreferences pref) {
        return pref.getString(IMAGE_PATH, null);
    }

    /*public static byte[] getImage(SharedPreferences pref) {
        String imageString = pref.getString(IMAGE_KEY, null);
        if (imageString != null) {
            return ImageUtil.decodeBytes(imageString);
        }
        return null;
    }*/

    public static void putUser(SharedPreferences pref, int form_number, User user) {
        User oldUser = getUser(pref);
        if (oldUser == null) {
            oldUser = new User();
        }
        switch(form_number) {
            case FIRST_FORM:
                oldUser.setFullName(user.getFullName());
                oldUser.setEmail(user.getEmail());
                oldUser.setPassword(user.getPassword());
                oldUser.setDob(user.getDob());
                oldUser.setFeet_height(user.getFeet_height());
                oldUser.setInches_height(user.getInches_height());
                oldUser.setRace(user.getRace());
                oldUser.setReligion(user.getReligion());
                break;
            case SECOND_FORM:
                oldUser.setGender(user.getGender());
                oldUser.setGenderInterest(user.getGenderInterest());
                oldUser.setMin_range(user.getMin_range());
                oldUser.setMax_range(user.getMax_range());
                oldUser.setZipCode(user.getZipCode());
                break;
        }

        String userString = gson.toJson(oldUser, User.class);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY, userString);
        editor.apply();
    }

    public static void removeUser(SharedPreferences pref) {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(KEY);
    }
}
