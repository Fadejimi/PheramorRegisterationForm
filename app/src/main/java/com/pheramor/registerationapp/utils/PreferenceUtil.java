package com.pheramor.registerationapp.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pheramor.registerationapp.retrofit.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class PreferenceUtil {
    private static final String KEY = "user_pref";
    public static final String PREF = "my_pref";
    public static final int FIRST_FORM = 1;
    public static final int SECOND_FORM = 2;
    public static final int THIRD_FORM = 3;
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
            case THIRD_FORM:
                oldUser.setPictureString(user.getPictureString());
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
