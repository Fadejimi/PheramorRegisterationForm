package com.pheramor.registerationapp.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.pheramor.registerationapp.retrofit.models.User;
import com.pheramor.registerationapp.utils.PreferenceUtil;
import com.pheramor.registerationapp.view_interfaces.SecondFragmentInterface;
import com.pheramor.registerationapp.view_interfaces.SecondRegisterationInterface;
import com.pheramor.registerationapp.views.SecondRegisterationFragment;

import java.util.ArrayList;
import java.util.List;

public class SecondRegisterPresentation implements SecondRegisterationInterface {
    private SecondFragmentInterface fragment;
    private static final String TAG = SecondRegisterPresentation.class.getSimpleName();

    public SecondRegisterPresentation(SecondFragmentInterface fragment) {
        this.fragment = fragment;
    }


    @Override
    public String getAgeValue(int value, int thumbIndex, String ageText) {
        Log.d(TAG, "ageText value: " + ageText);
        String[] ageRange = ageText.split("-");
        if (thumbIndex == 0) {
            ageRange[0] = "Age Range (" + value;
        }
        else {
            if (value == 60) ageRange[1] = value + "+ )";
            else ageRange[1] = value + ") ";
        }

        return ageRange[0].trim() + " - " + ageRange[1].trim();
    }

    private String stripSpaces(String st) {
        return st.replaceAll("\\s", "");
    }
    @Override
    public String setValid(String gender, boolean men, boolean female, int minAge, int maxAge,
                           String zipCode) {
        if (gender == null || gender.isEmpty()) {
            return "Please select your gender";
        }
        if (!men && !female) {
            return "Please select your relationship interests";
        }
        if (zipCode == null || zipCode.isEmpty()) {
            return "Please enter your zipcode";
        }
        return "";
    }

    @Override
    public void sendData(String gender, boolean men, boolean female, int minAge, int maxAge,
                         String zipCode) {
        List<String> interests = new ArrayList<>();
        if (men) interests.add("Men");
        if (female) interests.add("Women");

        User user = new User();
        user.setGender(gender);
        user.setGenderInterest(interests);
        user.setMin_range(minAge);
        user.setMax_range(maxAge);
        user.setZipCode(zipCode);

        SharedPreferences preferences = ((SecondRegisterationFragment) fragment).getActivity().getSharedPreferences(
                PreferenceUtil.PREF, Context.MODE_PRIVATE);
        PreferenceUtil.putUser(preferences, PreferenceUtil.SECOND_FORM, user);
    }

    @Override
    public User getUser() {
        SharedPreferences preferences = ((SecondRegisterationFragment) fragment).getActivity().getSharedPreferences(
                PreferenceUtil.PREF, Context.MODE_PRIVATE);
        return PreferenceUtil.getUser(preferences);
    }
}
